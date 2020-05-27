package xyz.rieproject.cores

import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.Logger
import xyz.rieproject.cores.ListenerAdapterManager.Companion.connectionManager as database
import xyz.rieproject.models.GuildModel
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory

class GuildManager: ListenerAdapter() {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val guild = event.guild
        val id = guild.id
        val data = database.get<GuildModel>(id)
        if (data === null) {
            console.info("Data inserted for guild $id namely ${guild.name}.")
            database.set<GuildModel>(id, GuildModel(id))
        }
    }
}