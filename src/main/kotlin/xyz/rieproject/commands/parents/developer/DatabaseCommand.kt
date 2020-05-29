package xyz.rieproject.commands.parents.developer

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import xyz.rieproject.commands.childs.developer.DatabaseRead

class DatabaseCommand: Command() {
    init {
        name = "database"
        aliases = listOf("db", "mongo", "mongod", "mongodb", "data").toTypedArray()
        help = "set of specific command used to manipulate database"
        ownerCommand = true
        hidden = true
        children = listOf(DatabaseRead()).toTypedArray()
    }
    override fun execute(event: CommandEvent) {
        TODO("Not yet implemented")
    }
}