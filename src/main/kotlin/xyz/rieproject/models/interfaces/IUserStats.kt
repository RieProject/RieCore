package xyz.rieproject.models.interfaces

interface IUserStats {
    /**
     * Snowflake User dari Discord.
     */
    val _id: String

    /**
     * Kapan user ini pertama kali main.
     */
    val start_playing: String

    /**
     * Level saat ini, multipler dari session aja.
     */
    val level: Int

    /**
     * XP.
     */
    val xp: Int
}