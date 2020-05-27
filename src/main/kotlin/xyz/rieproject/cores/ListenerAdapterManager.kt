package xyz.rieproject.cores

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.mongodb.client.MongoDatabase
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.sharding.ShardManager
import org.apache.logging.log4j.Logger
import org.reflections.Reflections
import xyz.rieproject.Config
import xyz.rieproject.NeoClusterSharding
import xyz.rieproject.sub.engines.IGame
import xyz.rieproject.utils.CConsole

import java.lang.management.ManagementFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class ListenerAdapterManager(private val jda: JDA): ListenerAdapter() {
    private val builder = CommandClientBuilder()
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    private lateinit var database: MongoDatabase
    override fun onReady(event: ReadyEvent) {
        connectionManager = ConnectionManager()
        database = connectionManager.getDatabase()
        val selfUser = event.jda.selfUser
        builder
            .setPrefix(Config.PREFIX)
            .setAlternativePrefix("$")
            .setLinkedCacheSize(0)
            .setListener(listener)
            .setScheduleExecutor(threadpool)

        jda.addEventListener(waiter)
        Reflections("xyz.rieproject.commands")
            .getSubTypesOf(Command::class.java)
            .forEach {
                builder.addCommand(it.newInstance())
            }
        builder.setOwnerId(Config.OWNER_ID[0].toString())
        Config.OWNER_ID.forEach {
            builder.setCoOwnerIds(it.toString())
        }
        val clientBuilder = builder.build()
        jda.addEventListener(clientBuilder)
        jda.addEventListener(guildManager)
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

        threadpool.scheduleWithFixedDelay({ System.gc() }, 12, 6, TimeUnit.HOURS)
    }

    override fun onShutdown(event: ShutdownEvent) {
        jda.presence.setPresence(
            OnlineStatus.ONLINE, Activity.listening(
                "Shutting down..."
            )
        )
    }

    companion object {
        lateinit var connectionManager: ConnectionManager
        val guildManager = GuildManager()
        val waiter: EventWaiter = EventWaiter()
        val GAME_SESSIONS: HashMap<String, IGame> = HashMap()
        val listener = CommandExceptionListener()
        val threadpool: ScheduledExecutorService = Executors.newScheduledThreadPool(100)
        val shards: ShardManager = NeoClusterSharding.shards
    }
}