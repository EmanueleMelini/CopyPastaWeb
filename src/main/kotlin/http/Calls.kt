package http

import http.responses.GroupResponse
import http.responses.LoginResponse
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import model.GroupModel
import model.UserModel
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.coroutines.CoroutineContext
import kotlin.js.json

class Calls(private val ctx: CoroutineContext) {

    suspend fun loginCall(username: String, password: String): String {

        return withContext(ctx) {

            val response = window.fetch("http://192.168.1.43:8080/login", RequestInit().apply {
                method = "POST"
                headers = json().apply {
                    this["Content-Type"] = "application/json"
                }
                mode = RequestMode.CORS
                body = JSON.stringify(
                    UserModel(
                        0,
                        username,
                        password,
                        0,
                        groupAdmin = false,
                        admin = false,
                        deleted = false
                    )
                )
            }).await()

            response.text().await()

        }

    }

    suspend fun groupCall(loginResponse: LoginResponse): String {

        return withContext(ctx) {

            val response = window.fetch("http://192.168.1.43:8080/group/user", RequestInit().apply {
                method = "GET"
                headers = json().apply {
                    this["Content-Type"] = "application/json"
                    this["Authorization"] = "Bearer ${loginResponse.token}"
                }
                mode = RequestMode.CORS
            }).await()

            response.text().await()

        }

    }

    suspend fun copyCall(loginResponse: LoginResponse): String {

        return withContext(ctx) {

            val response = window.fetch("http://192.168.1.43:8080/copy/group", RequestInit().apply {
                method = "GET"
                headers = json().apply {
                    this["Content-Type"] = "application/json"
                    this["Authorization"] = "Bearer ${loginResponse.token}"
                }
                mode = RequestMode.CORS
            }).await()

            response.text().await()

        }

    }

    suspend fun deleteGroup(groupResponse: GroupResponse, loginResponse: LoginResponse, groupModel: GroupModel): String {

        return withContext(ctx) {

            val response = window.fetch("http://192.168.1.43:8080/group/delete/" + groupModel.IDgroup, RequestInit().apply {
                method = "GET"
                headers = json().apply {
                    this["Content-Type"] = "application/json"
                    this["Authorization"] = "Bearer ${loginResponse.token}"
                }
                mode = RequestMode.CORS
            }).await()

            response.text().await()

        }

    }

}