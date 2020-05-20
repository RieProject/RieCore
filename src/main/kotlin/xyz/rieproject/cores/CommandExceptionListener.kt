package xyz.rieproject.cores

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.command.CommandListener
import net.dv8tion.jda.api.entities.ChannelType
import org.slf4j.LoggerFactory
import xyz.rieproject.utils.CConsole
import xyz.rieproject.utils.Usage
import java.lang.management.ManagementFactory


class CommandExceptionListener: CommandListener {
    private val console = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    private val usage: Usage = Usage()
    override fun onCommandException(event: CommandEvent, command: Command, throwable: Throwable) {
        if (throwable is CommandErrorException) event.replyError(throwable.message) else if (throwable is CommandWarningException) event.replyWarning(
            throwable.message
        ) else console.error("An exception occurred in a command: $command", throwable)
    }

    class CommandErrorException(message: String?) : RuntimeException(message)
    class CommandWarningException(message: String?) : RuntimeException(message)

    override fun onCommand(event: CommandEvent, command: Command?) {
        if (event.isFromType(ChannelType.TEXT)) usage.increment(event.guild.idLong)
    }

    fun getUsage(): Usage {
        return usage
    }
}