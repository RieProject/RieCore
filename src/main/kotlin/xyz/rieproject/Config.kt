package xyz.rieproject

import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import java.awt.Color
import java.time.OffsetDateTime

object Config {
    val USE_SHARDING: Boolean = if (System.getenv("SHARDING").isNullOrEmpty()) false else true
    val SHARD_COUNT: Int = if (System.getenv("SHARD_COUNT").isNullOrEmpty()) 1 else Integer.parseInt(System.getenv("SHARD_COUNT"))
    val PREFIX = "rr"
    var STATUS = "Shuffling Cards!"
    val VERSION = if (this.javaClass.`package`.implementationVersion.isNullOrEmpty()) "DEVELOPMENT" else this.javaClass.`package`.implementationVersion.isNullOrEmpty()
    val TOKEN = System.getenv("TOKEN")
    val OWNER_ID = listOf<Long>(337028800929857536.toLong(), 712222723169845319.toLong())
    val BOT_INVITE = "https://discordapp.com/oauth2/authorize?client_id=548464135638220810&permissions=0&scope=bot"

    var JDBC_URI = if (System.getenv("DATABASE_URI").isNullOrEmpty()) "jdbc:postgresql://localhost/cardwars" else System.getenv("DATABASE_URI")
    var JDBC_USER = if (System.getenv("DATABASE_USER").isNullOrEmpty()) "postgres" else System.getenv("DATABASE_URI")
    var JDBC_PASS = if (System.getenv("DATABASE_PASS").isNullOrEmpty()) null else System.getenv("DATABASE_URI")

    val SERVER_INVITE = "https://discord.gg/pFQXau5"
    val SUCCESS = "<:vSuccess:390202497827864597>"
    val WARNING = "<:vWarning:390208158699618306>"
    val ERROR = "<:vError:390229421228949504>"
    val LOADING = "<a:typing:393848431413559296>"
    val HELP_REACTION = SUCCESS.replace("<a?:(.+):(\\d+)>".toRegex(), "$1:$2")
    val ERROR_REACTION = ERROR.replace("<a?:(.+):(\\d+)>".toRegex(), "$1:$2")
    val STARTUP = OffsetDateTime.now()

    val SUCCESS_COLOR = Color(118, 255, 3)
    val FAILURE_COLOR = Color(239, 67, 63)
    val CONFIRMATION_COLOR = Color(102, 243, 255)

    fun isOwner(event: CommandEvent): Boolean {
        var owner = false
        OWNER_ID.forEach {
            if (event.message.author.idLong === it) owner = true
        }
        return owner
    }

    const val DEFAULT_CACHE_SIZE = 8000
    val PERMISSIONS: Array<Permission> = arrayOf<Permission>(
        Permission.ADMINISTRATOR,
        Permission.BAN_MEMBERS,
        Permission.KICK_MEMBERS,
        Permission.MANAGE_ROLES,
        Permission.MANAGE_SERVER,
        Permission.MESSAGE_ADD_REACTION,
        Permission.MESSAGE_ATTACH_FILES,
        Permission.MESSAGE_READ,
        Permission.MESSAGE_WRITE,
        Permission.MESSAGE_EMBED_LINKS,
        Permission.MESSAGE_HISTORY,
        Permission.MESSAGE_EXT_EMOJI,
        Permission.MESSAGE_MANAGE,
        Permission.VOICE_CONNECT,
        Permission.VOICE_MOVE_OTHERS,
        Permission.VOICE_DEAF_OTHERS,
        Permission.VOICE_MUTE_OTHERS,
        Permission.NICKNAME_CHANGE,
        Permission.NICKNAME_MANAGE,
        Permission.VIEW_AUDIT_LOGS
    )
    val NEED_PRO = "$WARNING Sorry, this feature requires Rie Premium Pass."
}