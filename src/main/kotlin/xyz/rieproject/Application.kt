package xyz.rieproject

import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import xyz.rieproject.cores.ListenerAdapterManager
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory

class Application(public val shards: ShardManager): ListenerAdapter() {
    private var logger: CConsole = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass)
    private var console = logger.sfl4jlogger
    val main_pid: String = ManagementFactory.getRuntimeMXBean().name
    var jda: JDA = JDABuilder(AccountType.BOT)
        .setToken(Config.TOKEN)
        .setBulkDeleteSplittingEnabled(false)
        .build()
    init {
        jda.presence.setPresence(OnlineStatus.ONLINE, Activity.watching("Loading..."))
        jda.addEventListener(ListenerAdapterManager(jda))
    }

    override fun onReady(event: ReadyEvent) {
        jda.presence.setPresence(
            OnlineStatus.ONLINE, Activity.watching(
                Config.PREFIX + "help | " + Config.STATUS
            ))
        jda.awaitReady()
        console.info("Discord API is Ready!")

        super.onReady(event)
    }
}