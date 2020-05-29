package xyz.rieproject.commands.parents.game

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.entities.Category
import xyz.rieproject.sub.engines.cah.Game

class GameCommand: Command() {
    init {
        name = "game"
        aliases = listOf("cardgame", "makesession").toTypedArray()
    }
    @ExperimentalStdlibApi
    override fun execute(event: CommandEvent) {
        val host = event.member.id
        val category = event.textChannel.parent as net.dv8tion.jda.api.entities.Category
        val game = Game(host, category, mutableListOf<String>("a"), mutableListOf<String>("b"))
        game.createSessionCollector(event)
        Thread().run {
            Thread.sleep(5000)
            game.startGame(event)
        }
    }
}