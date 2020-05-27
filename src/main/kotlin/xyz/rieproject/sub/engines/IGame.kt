package xyz.rieproject.sub.engines

import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import xyz.rieproject.sub.engines.cah.Player

interface IGame {
    var sessionController: Category
    @ExperimentalStdlibApi
    val players: HashMap<String, Player>
    val host: String
    val winner: User?
    val sessionCollector: Any?
}