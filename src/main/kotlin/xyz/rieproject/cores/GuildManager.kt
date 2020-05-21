package xyz.rieproject.cores

import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.Logger
import xyz.rieproject.models.DataMap
import xyz.rieproject.models.GuildModel
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory

class GuildManager: ListenerAdapter() {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    private val database = ListenerAdapterManager.guildDatabase

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val guild = event.guild
        if (database[guild.id].isNullOrEmpty()) {
            GuildModel(guild.id, guild)
        }
    }
}