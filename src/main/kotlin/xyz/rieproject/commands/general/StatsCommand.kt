package xyz.rieproject.commands.general

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.sun.management.OperatingSystemMXBean
import net.dv8tion.jda.api.JDAInfo
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import xyz.rieproject.Config
import java.lang.management.ManagementFactory
import javax.management.Attribute
import javax.management.ObjectName

class StatsCommand: Command() {
    init {
        name = "stats"
        help = "prints all information regarding system statistics"
        guildOnly = false
        botPermissions = arrayOf<Permission>(Permission.MESSAGE_EMBED_LINKS)
    }
    override fun execute(event: CommandEvent) {
        val osBean = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java)
//        val usageMem = (osBean.totalPhysicalMemorySize - osBean.freePhysicalMemorySize) / 1000000

        // Runtime from JVM
        val freeMemory = Runtime.getRuntime().freeMemory() / 1000000
        val totalMemory = Runtime.getRuntime().totalMemory() / 1000000
        val maxMemory = Runtime.getRuntime().maxMemory() / 1000000

        // Memory used by JVM
        val usageMem = maxMemory - freeMemory

        // Total MEM installed on a machine
        val totalMem = osBean.totalPhysicalMemorySize / 1000000

        val messageBuilder = MessageBuilder()
            .append("**Rie Project**\n")
            .append("```js\n")
            .append("Version: \"${Config.VERSION}\"\n")
            .append("ID: \"${event.jda.selfUser.id}\"\n")
            .append("Guilds: \"${event.jda.guildCache.size()}\"\n")
            .append("Users: \"${event.jda.userCache.size()}\"\n")
            .append("```\n")
            .append("**Development Information**\n")
            .append("```js\n")
            .append("JDK: \"Java 8\"\n")
            .append("Kotlin: \"${KotlinVersion.CURRENT.toString()}\"\n")
            .append("Library: \"JDA-v${JDAInfo.VERSION}\"\n")
            .append("OS: \"${Config.OS}\"\n")
            .append("Memory Usage: \"${usageMem}MB/${totalMem}MB\"\n")
            .append("CPU Usage: \n")
            .append("Threads: \"${Thread.activeCount()}\"\n")
            .append("System: \"${processCpuLoad}%\"")
            .append("```\n")
        event.channel.sendMessage(messageBuilder.build()).queue()
    }

    companion object {
        val processCpuLoad: Double
            @Throws(Exception::class)
            get() {
                val mbs = ManagementFactory.getPlatformMBeanServer()
                val name = ObjectName.getInstance("java.lang:type=OperatingSystem")
                val list = mbs.getAttributes(name, arrayOf("ProcessCpuLoad"))

                if (list.isEmpty()) return java.lang.Double.NaN

                val att = list[0] as Attribute
                val value = att.value as Double

                return if (value == -1.0) java.lang.Double.NaN else (value * 1000).toInt() / 10.0
            }
    }
}