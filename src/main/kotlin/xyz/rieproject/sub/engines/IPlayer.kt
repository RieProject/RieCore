package xyz.rieproject.sub.engines

import net.dv8tion.jda.api.entities.User

interface IPlayer {
    val game: IGame
    val user: User
    val id: String
}