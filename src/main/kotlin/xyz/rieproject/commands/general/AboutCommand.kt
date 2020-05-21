package xyz.rieproject.commands.general

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDAInfo
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.sharding.ShardManager
import org.apache.logging.log4j.core.tools.picocli.CommandLine
import xyz.rieproject.Application
import xyz.rieproject.NeoClusterSharding
import xyz.rieproject.cores.ListenerAdapterManager
import java.awt.Color

class AboutCommand: Command() {
    init {
        name = "about"
        help = "shows info about the bot"
        guildOnly = false
        botPermissions = arrayOf<Permission>(Permission.MESSAGE_EMBED_LINKS)
    }
    override fun execute(event: CommandEvent) {
        val sm: ShardManager = ListenerAdapterManager.shards
        event.reply(
            MessageBuilder()
                .setContent("**Rie Project**")
                .setEmbed(
                    EmbedBuilder()
                        .setColor(if (event.guild == null) Color.GRAY else event.selfMember.color)
                        .setDescription(
                            """
                                おはよ, I am **Rie**#8161 (理恵), a bot designed to host your desired and prefered card games!
                                I was written in Kotlin by **Riichi_Rusdiana**#6815 and **Stellarz_Munn**#4554 using [JDA](${JDAInfo.GITHUB}) and [JDA-Utilities](${JDAUtilitiesInfo.GITHUB})
                                For more system-specific information, type `${event.client.prefix} stats`  
                                Type `${event.client.prefix}
                                """.trimIndent() + event.client
                                .helpWord + "` for help and information.\n\n"
                                    + "Check our [GitHub Link](https://github.com/RieProject) for contribution\n" +
                                    "Join our [support server](https://discord.gg/342FkSe) too!"
                        )
                        .addField(
                            "Stats",
                            """${sm.shardsTotal} Shards
${sm.guildCache.size()} Servers""",
                            true
                        )
                        .addField(
                            "",
                            sm.userCache.size()
                                .toString() + " Users\n" + Math.round(sm.averageGatewayPing) + "ms Avg Ping",
                            true
                        )
                        .addField(
                            "",
                            """${sm.textChannelCache.size()} Text Channels
""" + sm.voiceChannelCache
                                .size() + " Voice Channels",
                            true
                        )
                        .setFooter("Last restart", null)
                        .setTimestamp(event.client.startTime)
                        .build()
                )
                .build()
        )
    }
}