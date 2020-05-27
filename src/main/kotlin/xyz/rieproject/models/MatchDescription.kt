package xyz.rieproject.models

import kotlinx.serialization.Serializable
import xyz.rieproject.models.interfaces.cah.IMatchDescription

@Serializable
data class MatchDescription(
    override val get_voted: Int,
    override val accumulation_point: Int,
    override val win: Boolean
): IMatchDescription