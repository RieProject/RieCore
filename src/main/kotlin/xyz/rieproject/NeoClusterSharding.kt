package xyz.rieproject

import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import xyz.rieproject.cores.BlockingSessionController
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import javax.security.auth.login.LoginException

object NeoClusterSharding {
    private val console = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger!!
    public lateinit var shards: ShardManager
    @Throws(LoginException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val builder = DefaultShardManagerBuilder()
        builder.setToken(Config.TOKEN)
        builder.setShardsTotal(Config.SHARD_COUNT)
        builder.setBulkDeleteSplittingEnabled(false)
        builder.setRequestTimeoutRetry(true)
        builder.setSessionController(BlockingSessionController())
        shards = builder.build()

        shards.addEventListener(Application(shards))
        console.info("[Shard System] Shard Manager spawned!")
    }
}