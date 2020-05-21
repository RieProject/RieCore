package xyz.rieproject.sub.engines

import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

class GameCore {
    var sessionChannel: MessageChannel? = null
    val players: List<String> = ArrayList()
}