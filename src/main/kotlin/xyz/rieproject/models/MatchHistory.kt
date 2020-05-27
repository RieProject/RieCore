package xyz.rieproject.models

import kotlinx.serialization.Serializable
import xyz.rieproject.models.interfaces.cah.IMatchHistory

@Serializable
data class MatchHistory(
    override val games_round: Int,
    override val match_start: String,
    override val match_end: String,
    override val matches: List<MatchDescription>
): IMatchHistory