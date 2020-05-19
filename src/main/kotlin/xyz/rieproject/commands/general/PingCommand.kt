package xyz.rieproject.commands.general

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import java.time.temporal.ChronoUnit

class PingCommand: Command() {
    init {
        name = "ping"
        help = "pinging discord server"
        guildOnly = false
        botPermissions = arrayOf<Permission>(Permission.MESSAGE_EMBED_LINKS)
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessage("Pinging...").queue {
            val latency = event.message.timeCreated.until(it.timeCreated, ChronoUnit.MILLIS)
            it.editMessage("Ping: " + latency + "ms || Websocket: " + event.jda.gatewayPing + "ms").queue()
        }
    }
}