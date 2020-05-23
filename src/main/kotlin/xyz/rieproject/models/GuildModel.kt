package xyz.rieproject.models

import xyz.rieproject.models.interfaces.IGuildData

class GuildModel(override val data: IGuildData?): Model<IGuildData?>("guild") {
    init {
    }
}