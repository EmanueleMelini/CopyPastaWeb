package model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val IDuser: Int,
    val username: String,
    val password: String,
    val IDgroup: Int,
    val groupAdmin: Boolean,
    val admin: Boolean,
    val deleted: Boolean
)