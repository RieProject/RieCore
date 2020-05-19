package xyz.jdev.utils

@Suppress("SpellCheckingInspection")
object Javachalk {
    val ANSI_RESET = "\u001B[0m"
    val ANSI_BLACK = "\u001B[30m"
    val ANSI_RED = "\u001B[31m"
    val ANSI_GREEN = "\u001B[32m"
    val ANSI_YELLOW = "\u001B[33m"
    val ANSI_BLUE = "\u001B[34m"
    val ANSI_PURPLE = "\u001B[35m"
    val ANSI_CYAN = "\u001B[36m"
    val ANSI_WHITE = "\u001B[37m"
    val ANSI_BLACK_BACKGROUND = "\u001B[40m"
    val ANSI_RED_BACKGROUND = "\u001B[41m"
    val ANSI_GREEN_BACKGROUND = "\u001B[42m"
    val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
    val ANSI_BLUE_BACKGROUND = "\u001B[44m"
    val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
    val ANSI_CYAN_BACKGROUND = "\u001B[46m"
    val ANSI_WHITE_BACKGROUND = "\u001B[47m"

    fun black(string: String): String { return ANSI_BLACK + string + ANSI_RESET }
    fun red(string: String): String { return ANSI_RED + string + ANSI_RESET }
    fun green(string: String): String { return ANSI_GREEN + string + ANSI_RESET }
    fun yellow(string: String): String { return ANSI_YELLOW + string + ANSI_RESET }
    fun blue(string: String): String { return ANSI_BLUE + string + ANSI_RESET }
    fun purple(string: String): String { return ANSI_PURPLE + string + ANSI_RESET }
    fun cyan(string: String): String { return ANSI_CYAN + string + ANSI_RESET }
    fun white(string: String): String { return ANSI_WHITE + string + ANSI_RESET }
    fun bgBlack(string: String): String { return ANSI_BLACK_BACKGROUND + string + ANSI_RESET }
    fun bgRed(string: String): String { return ANSI_RED_BACKGROUND + string + ANSI_RESET }
    fun bgGreen(string: String): String { return ANSI_GREEN_BACKGROUND + string + ANSI_RESET }
    fun bgYellow(string: String): String { return ANSI_YELLOW_BACKGROUND + string + ANSI_RESET }
    fun bgBlue(string: String): String { return ANSI_BLUE_BACKGROUND + string + ANSI_RESET }
    fun bgPurple(string: String): String { return ANSI_PURPLE_BACKGROUND + string + ANSI_RESET }
    fun bgCyan(string: String): String { return ANSI_CYAN_BACKGROUND + string + ANSI_RESET }
    fun bgWhite(string: String): String { return ANSI_WHITE_BACKGROUND + string + ANSI_RESET }
}