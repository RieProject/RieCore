package xyz.rieproject.sub.engines.cah

import net.dv8tion.jda.api.entities.User
import xyz.rieproject.sub.engines.IGame
import xyz.rieproject.sub.engines.IPlayer

@ExperimentalStdlibApi
class Player(override val game: Game, override val user: User) : IPlayer {
    override val id = user.id
    var points = 0
    var strikes = 0
    val kickables = strikes >= 3
    val hand = mutableSetOf<String>()

    fun dealHand(): MutableSet<String> {
        if (hand.size > 9) return hand
        val drawCount = 10 - hand.size
        val i = 0
        while(i < drawCount) hand.add(game.whiteDeck.draw())
        return hand
    }

    fun turn(): Int {
        if (id === game.getCzar().user.id) return 0
        dealHand()
        try {
            // TODO("Adding method for picking cards")
        } catch (e: Error) {
            strikes++
            return 0
        }
        return 0
    }
}