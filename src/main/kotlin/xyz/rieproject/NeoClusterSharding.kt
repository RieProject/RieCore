package xyz.rieproject

import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import javax.security.auth.login.LoginException

object NeoClusterSharding {
    private val console = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger!!
    @Throws(LoginException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val builder = DefaultShardManagerBuilder()
        builder.setToken(Config.TOKEN)
        builder.setShardsTotal(Config.SHARD_COUNT)
        builder.addEventListeners(Application())
        builder.build()
        console.info("[Shard System] Shard Manager spawned!")
    }
}