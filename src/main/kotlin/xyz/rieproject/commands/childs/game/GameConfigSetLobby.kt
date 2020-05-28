package xyz.rieproject.commands.childs.game

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.litote.kmongo.updateOne
import xyz.rieproject.cores.ListenerAdapterManager.Companion.connectionManager
import xyz.rieproject.models.GuildModel

class GameConfigSetLobby: Command() {
    init {
        name = "setlobby"
        help = "set category for your lobby"
        guildOnly = true
        cooldown = 10
        userPermissions = arrayOf(Permission.MANAGE_SERVER)
    }
    override fun execute(event: CommandEvent) {
        val guild = event.guild
        val col = connectionManager.getCollection<GuildModel>()
        val category = event.message.category?.id
        col.updateOne(GuildModel::_id eq guild.id, setValue(GuildModel::category_id, category))
        event.reply("Category Lobby has been reconfigured to this channel!")
    }
}