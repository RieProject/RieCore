package xyz.rieproject.models

import xyz.rieproject.models.interfaces.IGuildData

data class GuildModel(
    override val _id: String,
    override var locale: String? = "USA",
    override var category_id: String? = null): IGuildData