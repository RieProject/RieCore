package xyz.rieproject.commands.parents.game

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import xyz.rieproject.commands.childs.game.GameConfigSetLobby

class GameConfigCommand: Command() {
    init {
        name = "gameconfig"
        aliases = listOf("gconf", "gameconf", "setgame").toTypedArray()
        children = listOf(GameConfigSetLobby()).toTypedArray()
    }
    override fun execute(event: CommandEvent) {
    }
}