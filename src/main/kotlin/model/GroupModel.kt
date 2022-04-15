package model

import kotlinx.serialization.Serializable

@Serializable
data class GroupModel(
    val IDgroup: Int,
    val name: String,
    val description: String,
    val deleted: Boolean
)