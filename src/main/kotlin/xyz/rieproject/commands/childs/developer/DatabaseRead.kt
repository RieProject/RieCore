package xyz.rieproject.commands.childs.developer

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import xyz.rieproject.cores.ListenerAdapterManager.Companion.connectionManager

class DatabaseRead: Command() {
    val database = connectionManager.getDatabase()
    init {
        name = "read"
        help = "read directly into database"
        ownerCommand = true
    }
    override fun execute(event: CommandEvent) {
        TODO("Not yet implemented")
    }
}