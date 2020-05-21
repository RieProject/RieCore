package xyz.rieproject.models

import net.dv8tion.jda.api.entities.Guild
import xyz.rieproject.cores.ListenerAdapterManager.Companion.guildDatabase

class GuildModel(private val id: String, val guild: Guild?): Model() {
    override var database = guildDatabase

    // Meta
    var name: String?
        get() = database["guild.${id}.name"]
        set(v: String?) {
            database["guild.${id}.name"] = v as String
        }
    var region: String?
        get() = database["guild.${id}.region"]
        set(v: String?) {
            database["guild.${id}.region"] = v as String
        }

    // Configuration
    var locale: String?
        get() = database["guild.${id}.locale"]
        set(value) {
            database["guild.${id}.locale"]
        }

    // Images
    var ICON_URL: String?
        get() = database["guild.${id}.icon_url"]
        set(v: String?) {
            database["guild.${id}.icon_url"] = v as String
        }
    var SPLASH_URL: String?
        get() = database["guild.${id}.splash_url"]
        set(v: String?) {
            database["guild.${id}.splash_url"] = v as String
        }
    var BANNER_URL: String?
        get() = database["guild.${id}.banner_url"]
        set(v: String?) {
            database["guild.${id}.banner_url"] = v as String
        }

    init {
        if (guild != null) {
            name = guild.name
            region = guild.region.toString()
            locale = "USA"
            ICON_URL = guild.iconUrl
            SPLASH_URL = guild.splashUrl
            BANNER_URL = guild.bannerUrl
        }
    }
}