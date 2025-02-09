package com.hyprland.settings.parser.loadHyprlandSettings


import com.hyprland.settings.parser.loadHyprlandSettings.exceptions.HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException
import com.hyprland.settings.parser.loadHyprlandSettings.exceptions.HyprlandSettingsFileIsEmptyException
import com.hyprland.settings.parser.loadHyprlandSettings.result.*
import com.hyprland.settings.parser.validatePath.ValidatePath
import com.hyprland.settings.parser.validatePath.exceptions.*
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path

/**
 * ### [LoadHyprlandSettings]
 *
 * ***Package*** [com.hyprland.settings.parser.loadHyprlandSettings]
 *
 * This will load all the valid and invalid settings into the memory for
 * further use
 *
 * ###### Methods
 * - [load] This will load all the settings.
 */
class LoadHyprlandSettings {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    /**
     * ###### Instant of [ValidatePath]
     *
     * This will use to validate hyprland file path and source file path
     */
    private val validatePath = ValidatePath()

    /** This get `Username` of linux system */
    private val home = System.getProperty("user.home")

    private var pathToHyprland = Path.of("")

    /** This will hold all valid and invalid lines of hyprland settings file */
    private val validSettingsFile: MutableList<LineObject> = mutableListOf()

    /** This will temporarily hold hyprlang settings */
    private val hyprlangHolder: MutableList<String> = mutableListOf()

    /**
     * These will use for checking if it needs to add next line in
     * [hyprlangHolder]
     */
    private var addToHyprlandHolder = false
    private var hyprlandOpen = 0

    /**
     * ##### function load
     *
     * This method will try to load all the hyprland settings files from
     * `Default Path` and make a list of settings.
     *
     * @return Result<[ResultObjects]<[LineObject]>
     * - `onSuccess`, this will return a [ResultObjects] of [LineObject]
     * - `onFailure`, this will return exceptions
     *
     * @throws HyprlandSettingsFileDoseNotExist
     * - When hyprland file does not exist. Try to change the default path
     * - Can be recovered
     *
     * @throws HyprlandSettingsFileIsNotReadable
     * - When hyprland file is not readable. Try to change permissions
     * - Can be recovered
     *
     * @throws HyprlandSettingsFileIsEmptyException
     * - When hyprland file is empty
     * - Could be recover if default path change or add new settings
     *
     * @throws HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException
     * - This happens if hyprland settings file successfully read but for some
     *   way could not be load to memory
     * - Cannot recover. If this happens it is a bug
     */
    fun load(): Result<ResultObjects<LineObject>> = runCatching {

        /** This gets a valid path to hyprland.conf file if is successfully */
        val path = validatePath.validateHyprlandPath()
            .getOrElse { exception: Throwable ->

                /**
                 * Return fail result because of an invalid path.
                 *
                 * @exception HyprlandSettingsFileIsEmptyException
                 * @exception HyprlandSettingsFileIsNotReadable
                 */
                return Result.failure(exception)

            }.also {

                pathToHyprland = it
            }


        /**
         * Read all the line from hyprland and if it not empty try to load this
         * file into memory or throw [HyprlandSettingsFileIsEmptyException]
         */
        Files.readAllLines(path)
            .takeIf {
                it.isNotEmpty()
            }
            ?.let { // if read line is not empty -> true
                createSettingsFile(it)
            }
            ?: throw HyprlandSettingsFileIsEmptyException() // if read line is empty -> false


        /**
         * Return result object if successful but if that is empty some
         * unknown error has been percent in code that way it will throw
         * [HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException]
         */
        return validSettingsFile
            .takeIf {
                it.isNotEmpty()
            }
            ?.let {
                return Result.success(
                    ResultObjects(
                        metaData = MetaData(
                            statusCode = 200,
                            type = Type.OK,
                            errorTags = Tags.NO_ERROR,
                            message = ""
                        ),
                        body = validSettingsFile
                    )
                )
            }
            ?: throw HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException()
    }

    private fun createSettingsFile(loadFile: List<String>) = runCatching {

        /**
         * Regex format for seeing what kind of line is it is.
         * 1. `regexNormalFormat` => keyword = values
         * 2. `regexHyprlangFormat` => variable {
         */
        val regexNormalFormat = """^\s*([^\s=]+)\s*=\s*(\S.*)?\s*$""".toRegex()

        val regexHyprlangFormat = """\w+\s?\{""".toRegex()


        loadFile.forEach { line: String ->

            val trimLine = line.trim()

            if (trimLine.isEmpty()) {
                return@forEach
            }

            /** This use for remove lines link comments and empty line and empty spaces */
            if (!regexNormalFormat.matches(trimLine) && !regexHyprlangFormat.matches(trimLine) && trimLine.last() != '}') {
                return@forEach
            }

            /** Check if the line is part of hyprland. specifically `Keyword {` */
            regexHyprlangFormat.matches(trimLine)
                .takeIf { it } // check if it matches or not -> if it matches it runs let { }
                ?.let {
                    addToHyprlandHolder = true
                    hyprlandOpen++

                    hyprlangHolder.add(trimLine.replace("\\s".toRegex(), ""))

                    return@forEach
                }


            /**
             * Only when [addToHyprlandHolder] true that can we add line to
             * [hyprlangHolder] this hold hyprland code block like input { ... }. But
             * when last character of this line is `}` this it checks if this is the
             * end of that block. To do that we use [hyprlandOpen] and if it is the
             * last line, it will add line to memory and clear the holder for feather
             * use.
             */
            if (addToHyprlandHolder && trimLine.last() != '}') {

                hyprlangHolder.add(trimLine.replace("\\s".toRegex(), ""))

                return@forEach

            } else {

                if (trimLine.last() == '}') {
                    hyprlandOpen--

                    hyprlandOpen
                        .takeIf { it != 0 }
                        ?.let {
                            hyprlangHolder.add(trimLine)
                            return@forEach
                        }
                        ?: run {
                            addToHyprlandHolder = false

                            addHyprlangToMemory()

                            return@forEach
                        }
                }

            }

            /**
             * After this `trimLine` are only regular settings. Lines that match
             * `Keyword = values`
             */
            val filterLine = trimLine.replace("\\s".toRegex(), "").split("=")

            if (filterLine[0] == "source") {

                val sourceLine = filterLine[1].split("#")[0]

                /**
                 * Get a full path for the source file
                 *
                 * ##### Valid way to import
                 * 1. Relative to home `~/`
                 * 2. Relative to the current path './'
                 * 3. Relative to parent folder '../'
                 */
                when {
                    sourceLine.startsWith('~') -> {

                        val relativePathFromHome: String = sourceLine.replace("~", home)
                        loadFromSourceFile(relativePathFromHome)
                    }

                    sourceLine.startsWith("..") -> {

                        val relativeToHyprlandFolder: String =
                            sourceLine.replaceRange(0..<2, "${pathToHyprland.parent.parent}")
                        loadFromSourceFile(relativeToHyprlandFolder)
                    }

                    sourceLine.startsWith('.') -> {

                        val relativePathFromHyprland: String = sourceLine.replaceFirst(".", "${pathToHyprland.parent}")
                        loadFromSourceFile(relativePathFromHyprland)
                    }

                    else -> {

                        addInvalidSourceLine()
                        return@forEach
                    }
                }

            } else {

                /**
                 * If line is not ether `Hyprlang` or a `source` line it will add into
                 * memory.
                 *
                 * `message` as the keyword `line` as values
                 */
                addValidLineToMemory(message = filterLine[0], line = filterLine[1])
            }

        }

    }.onFailure { exception: Throwable ->

        logger.warn(exception.message, exception)
    }


    /**
     * #### [loadFromSourceFile]
     *
     * This will take a source path and check if it exists and readable. Then
     * read all lines in that file and call [createSettingsFile] to load that
     * file settings into the memory.
     *
     * @param path source file as a [String]
     * @exception [Exception]
     * - This function itself does not throw any exceptions but it will recover
     *   form exception thrown by [ValidatePath]
     * - ***Exceptions That Will Be Handle are***
     *    - `FileDoseNotExistException` and `FileIsNotReadableException`
     *    - Others are just log. This need to be handled 'TODO'
     */
    private fun loadFromSourceFile(path: String): Result<Any> = runCatching {

        val loadSourceFile: Path = validatePath.validateSourceFileFromSettings(Path.of(path))
            .getOrThrow()

        Files.readAllLines(loadSourceFile)
            .takeIf { it.isNotEmpty() }
            ?.let {
                createSettingsFile(it)
            }
            ?: sourceFileErrorLineToMemory("Source file is empty")

    }.onFailure { exception: Throwable ->
        when (exception) {
            is FileDoseNotExistException -> exception.message?.let { sourceFileErrorLineToMemory(it) }

            is FileIsNotReadableException -> exception.message?.let { sourceFileErrorLineToMemory(it) }

            else -> logger.error(exception.message, exception)
        }
    }


    /**
     * ###### Helper function for [createSettingsFile]
     *
     * This will add what hold in [hyprlangHolder] to [validSettingsFile] by
     * combining all the strings.
     */
    private fun addHyprlangToMemory() {
        validSettingsFile.add(
            LineObject(
                error = false,
                message = "Hyprlang",
                line = hyprlangHolder.joinToString("\n")
            )
        )

        hyprlangHolder.clear()
    }

    /**
     * ###### Helper function for [createSettingsFile]
     *
     * This will add an error line to [validSettingsFile] for not having
     * excepted format of the source file type
     */
    private fun addInvalidSourceLine() {
        validSettingsFile.add(
            LineObject(
                error = true,
                message = "Invalid source file method. only valid types are -> `./` or `../` or `~/` or `/home/user/....`",
                line = ""
            )
        )
    }

    /**
     * ###### Helper function for [createSettingsFile]
     *
     * This will add valid lines to [validSettingsFile]
     */
    private fun addValidLineToMemory(message: String, line: String) {
        validSettingsFile.add(
            LineObject(
                error = false,
                message = message,
                line = line
            )
        )
    }


    /**
     * ###### Helper function for [loadFromSourceFile]
     *
     * This will add an error line if the source file couldn't be read or
     * doesn't exist
     */
    private fun sourceFileErrorLineToMemory(message: String) {
        validSettingsFile.add(
            LineObject(
                error = true,
                message = message,
                line = ""
            )
        )
    }
}