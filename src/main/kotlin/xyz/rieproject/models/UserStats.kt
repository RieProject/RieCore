package xyz.rieproject.models

import xyz.rieproject.models.interfaces.IUserStats

data class UserStats(
    override val _id: String,
    override val start_playing: String,
    override val level: Int,
    override val xp: Int
): IUserStats