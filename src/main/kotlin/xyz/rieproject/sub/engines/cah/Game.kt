package xyz.rieproject.sub.engines.cah

import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.requests.restaction.ChannelAction
import xyz.rieproject.sub.engines.IGame

@ExperimentalStdlibApi
class Game(
    override val host: String,
    override var sessionController: Category,
    private val whiteCards: MutableList<String>,
    private val blackCards: MutableList<String>): IGame {
    override val players = HashMap<String, Player>()
    override val winner: User? = null
    override val sessionCollector: Any? = null
    val czars = ArrayList<String>()
    val whiteDeck = Deck(whiteCards)
    val blackDeck = Deck(blackCards)

    fun addPlayer(user: User): HashMap<String, Player> {
        val player = Player(this, user)
        player.dealHand()
        players[player.id] = player
        if (!user.isBot) czars.add(player.id)
        return players
    }

    fun getCzar(): Player {
        return players[czars[0]] as Player
    }

    fun switchCzar(): Player? {
        czars.add(czars[0])
        czars.removeFirst()
        return getCzar()
    }

    fun kick(player: Player) {
        val id = player.id
        players.remove(id)
        czars.remove(id)
    }

    fun createSessionCollector() {
    }

    fun createTextChannel(name: String): ChannelAction<TextChannel> {
        return sessionController.createTextChannel(name)
    }

    fun createVoiceChannel(name: String): ChannelAction<VoiceChannel> {
        return sessionController.createVoiceChannel(name)
    }
}