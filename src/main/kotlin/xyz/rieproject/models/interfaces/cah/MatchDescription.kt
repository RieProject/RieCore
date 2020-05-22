package xyz.rieproject.models.interfaces.cah

interface MatchDescription {
    /**
     * Banyak vote yang dikumpulkan
     */
    val get_voted: Int

    /**
     * Jumlah poin akumulasi habis pertandingan
     */
    val accumulation_point: Int

    /**
     * Player ini menang atau tidak
     */
    val win: Boolean
}