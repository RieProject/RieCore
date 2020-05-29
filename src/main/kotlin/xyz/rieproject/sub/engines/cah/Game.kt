package xyz.rieproject.sub.engines.cah

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.restaction.ChannelAction
import xyz.rieproject.sub.engines.IGame
import xyz.rieproject.sub.engines.ReactionCollector

@ExperimentalStdlibApi
class Game(
    override val host: String,
    override var sessionController: Category,
    private val whiteCards: MutableList<String>,
    private val blackCards: MutableList<String>): IGame {
    override val players = HashMap<String, Player>()
    override val winner: User? = null
    override var sessionCollector: ListenerAdapter? = null
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

    fun createSessionCollector(event: CommandEvent) {
        val hostname = players[host]?.user?.name
        val embed = EmbedBuilder()
            .setAuthor("Hosted by $hostname")
            .setDescription("Card Against Humanity is a card game with sole purpose of compete and see who can come up with the best card to fill in the blank.")
            .build()
        val message = MessageBuilder().setContent("**Card Against Humanity Game**").setEmbed(embed).build()
        createTextChannel("rie-cah-$host").queue { textChannel ->
            textChannel.sendMessage(message).queue { sentMessage ->
                val message_id = sentMessage.id
                val emoji = "✅"
                sentMessage.addReaction(emoji)
                sessionCollector = ReactionCollector(message_id, emoji, hostname as String)
                event.jda.addEventListener(sessionCollector)
            }
        }
    }

    fun startGame(event: CommandEvent) {
        event.jda.removeEventListener(sessionCollector)
        Thread().run {
            event.channel.sendMessage("Game starting in 3").queue { m3 ->
                Thread.sleep(1000)
                m3.editMessage("Game starting in 2").queue { m2 ->
                    Thread.sleep(1000)
                    m2.editMessage("Game starting in 1").queue { m1 ->
                        Thread.sleep(1000)
                        m1.editMessage("Game start!")
                    }
                }
            }
        }
    }

    fun createTextChannel(name: String): ChannelAction<TextChannel> {
        return sessionController.createTextChannel(name)
    }

    fun createVoiceChannel(name: String): ChannelAction<VoiceChannel> {
        return sessionController.createVoiceChannel(name)
    }
}