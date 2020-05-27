package xyz.rieproject.models

import xyz.rieproject.models.interfaces.IGuildData

data class GuildModel(
    override val _id: String,
    override val locale: String? = "USA",
    override val role_id: String? = null,
    override val category_id: String? = null): IGuildData