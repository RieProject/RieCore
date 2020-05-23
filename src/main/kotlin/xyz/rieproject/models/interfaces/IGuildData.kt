package xyz.rieproject.models.interfaces

interface IGuildData {
    val _id: String

    /**
     * Bahasa yang digunakan. Default wajib EN.
     */
    val locale: String

    /**
     * Guild id, wajib ada pas ditambahin data
     */
    val guild_id: String

    /**
     * Role untuk join ke lobby
     */
    val role_id: String?

    /**
     * Channel tumpangan Rie untuk lobby
     */
    val channel_id: String?
}