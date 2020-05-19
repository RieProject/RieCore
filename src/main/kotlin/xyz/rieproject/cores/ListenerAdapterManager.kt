package xyz.rieproject.cores

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.Logger
import org.reflections.Reflections
import xyz.rieproject.Application
import xyz.rieproject.Config
import xyz.rieproject.utils.CConsole

import java.lang.management.ManagementFactory

class ListenerAdapterManager(private val jda: JDA): ListenerAdapter() {
    private val builder = CommandClientBuilder()
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    override fun onReady(event: ReadyEvent) {
        val selfUser = event.jda.selfUser
        builder.setPrefix(Config.PREFIX)
        builder.setAlternativePrefix("$")
        jda.addEventListener(waiter)
        Reflections("xyz.rieproject.commands")
            .getSubTypesOf(Command::class.java)
            .forEach {
                builder.addCommand(it.newInstance())
            }
        Config.OWNER_ID.forEach {
            builder.setOwnerId(it.toString())
        }
        val cbuild = builder.build()
        jda.addEventListener(cbuild)
        console.info(
            """
        |
        ||-========================================================
        || System ready to engage
        || Account Info: ${selfUser.name}#${selfUser.discriminator} (ID: ${selfUser.id})
        || Connected to ${event.jda.guilds.size} guilds, ${event.jda.textChannels.size} text channels
        || Prefix: ${Config.PREFIX}
        ||-========================================================
        |
        """.trimMargin("|")
        )
    }

    override fun onShutdown(event: ShutdownEvent) {
        jda.presence.setPresence(
            OnlineStatus.ONLINE, Activity.listening(
                "Shutting down..."
            )
        )
    }

    companion object {
        var waiter: EventWaiter = EventWaiter()
    }
}