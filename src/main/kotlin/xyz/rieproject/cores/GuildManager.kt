package xyz.rieproject.cores

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.Logger
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory

class GuildManager: ListenerAdapter() {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
    }
}