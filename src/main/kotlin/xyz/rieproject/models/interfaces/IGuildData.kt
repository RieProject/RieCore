package xyz.rieproject.models.interfaces

interface IGuildData {
    /**
     * Guild id, wajib ada pas ditambahin data
     */
    val _id: String

    /**
     * Bahasa yang digunakan. Default wajib EN.
     */
    val locale: String?

    /**
     * Role untuk join ke lobby
     */
    val role_id: String?

    /**
     * Category tumpangan Rie untuk lobby.
     */
    val category_id: String?
}