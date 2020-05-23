package xyz.rieproject.models.interfaces.cah

interface IMatchHistory {
    val _id: String

    /**
     * ID Match setiap kali bertanding
     */
    val id_match: String

    /**
     * Banyak black card yang dibuang dalam satu game
     */
    val games_round: Int

    /**
     * Timestamp ketika matchnya dimulai
     */
    val match_start: String

    /**
     * Timestamp ketika matchnya selesai
     */
    val match_end: String

    /**
     * Ringkasan match
     */
    val matches: List<IMatchDescription>
}