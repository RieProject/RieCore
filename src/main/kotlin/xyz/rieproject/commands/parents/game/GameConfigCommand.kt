package xyz.rieproject.commands.parents.game

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import org.apache.logging.log4j.Logger
import xyz.rieproject.commands.childs.game.GameConfigSetLobby
import xyz.rieproject.cores.ListenerAdapterManager.Companion.connectionManager
import xyz.rieproject.models.GuildModel
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory

class GameConfigCommand: Command() {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    init {
        name = "gameconfig"
        help = "config your server's game configuration"
        guildOnly = true
        aliases = listOf("gconf", "gameconf", "setgame").toTypedArray()
        children = listOf(GameConfigSetLobby()).toTypedArray()
        cooldown = 3
        userPermissions = arrayOf(Permission.MANAGE_SERVER)
    }
    override fun execute(event: CommandEvent) {
        val guild = event.guild
        val data = connectionManager.get<GuildModel>(guild.id)
        if (data != null) {
            event.reply("""
            Your current configuration is following:
            Category ID: ${data.category_id}
            Disabled Game: none
        """.trimIndent())
        } else {
            val iData = connectionManager.get<GuildModel>(guild.id)
            console.info("Data inserted for guild ${guild.id} namely ${guild.name}.")
            connectionManager.set<GuildModel>(guild.id, GuildModel(guild.id))
            event.reply("Config initialized for guild ${guild.name}!")
        }
    }
}