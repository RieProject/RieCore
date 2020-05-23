package xyz.rieproject.models.interfaces

interface IUserStats {
    val _id: String

    /**
     * Snowflake User dari Discord.
     */
    val id_user: String

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