package xyz.rieproject.commands.parents.developer

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import xyz.rieproject.cores.ListenerAdapterManager.Companion.connectionManager
import javax.script.ScriptEngineManager

class EvalCommand: Command() {
    override fun execute(event: CommandEvent) {
        event.channel.sendTyping().queue()
        event.async {
            val se = ScriptEngineManager().getEngineByName("Nashorn")
            se.put("bot", event.jda)
            se.put("event", event)
            se.put("jda", event.jda)
            se.put("guild", event.guild)
            se.put("channel", event.channel)
            se.put("database", connectionManager.getDatabase())
            val args: String = event.args.replace("([^(]+?)\\s*->", "function($1)")
            try {
                event.replySuccess("""
Evaluated Successfully:
```
${se.eval(args)}
```
""")
            } catch (e: Exception) {
                event.replyError("An exception was thrown:\n```\n$e ```")
            }
        }
    }

    init {
        name = "eval"
        help = "evaluates nashorn code"
        ownerCommand = true
        guildOnly = false
        hidden = true
    }
}