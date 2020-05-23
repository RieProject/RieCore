package xyz.rieproject.models.interfaces.cah

interface IMatchHistory {
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