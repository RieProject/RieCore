package xyz.rieproject.sub.engines

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReactionCollector(val message_id: String, val emoji: String, val hostname: String): ListenerAdapter() {
    val users = mutableListOf<User?>()
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.messageId == message_id) {
            val reaction = event.reactionEmote.emoji
            if (reaction == emoji) {
                val user = event.user
                user?.openPrivateChannel()?.queue {
                    if (it == null) {
                        event.channel.sendMessage("User ${user.asMention} cannot join the game due to disabled DM channel!").queue()
                    } else {
                        it.sendMessage("You were added to a game, hosted by $hostname!").queue()
                        users.add(user)
                    }
                }
            }
        }
    }

    override fun onMessageReactionRemove(event: MessageReactionRemoveEvent) {
        if (event.messageId == message_id) {
            val reaction = event.reactionEmote.emoji
            if (reaction == emoji) {
                val user = event.user
                user?.openPrivateChannel()?.queue {
                    if (it == null) {
                        event.channel.sendMessage("User ${user.asMention} cannot exit the game due to disabled DM channel!").queue()
                    } else {
                        it.sendMessage("You were removed from a game, hosted by $hostname!").queue()
                        users.remove(user)
                    }
                }
            }
        }
    }
}