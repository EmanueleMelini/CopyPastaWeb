package http.responses

import kotlinx.serialization.Serializable
import model.UserModel

@Serializable
data class LoginResponse(
    val error: Int,
    val message: String,
    val user: UserModel,
    val token: String
)