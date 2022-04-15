package pages

import csstype.ClassName
import http.Calls
import http.responses.LoginResponse
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import react.FC
import react.Props
import react.create
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.render
import react.useState

external interface LoginProps : Props {
    var username: String
    var password: String
    var cont: Element
}

val LoginPage = FC<LoginProps> { props ->
    var username by useState(props.username)
    var password by useState(props.password)
    val container by useState(props.cont)

    val mainScope = MainScope()

    val calls = Calls(mainScope.coroutineContext)

    div {
        className = ClassName("row text-center")

        div {
            className = ClassName("col-10 offset-1")

            div {
                className = ClassName("row")

                div {
                    className = ClassName("col-xxl-6 col-xl-6 col-md-6 col-sm-12 col-12 offset-xxl-3 offset-xl-3 offset-md-3 offset-sm-0 offset-0")

                    label {
                        className = ClassName("mt-5 title")
                        +"CopyPasta"
                    }

                    input {
                        className = ClassName("form form-control mt-5")
                        type = InputType.text
                        placeholder = "Inserisci l'username"
                        id = "username"
                        onChange = { event ->
                            username = event.target.value
                        }
                    }

                    input {
                        className = ClassName("form form-control mt-5")
                        type = InputType.password
                        placeholder = "Inserisci la password"
                        id = "password"
                        onChange = { event ->
                            password = event.target.value
                        }
                    }

                    button {
                        className = ClassName("btn btn-primary mt-5 login-btn")
                        type = ButtonType.button
                        onClick = {
                            if (username.isNotEmpty() && password.isNotEmpty())
                                mainScope.launch {
                                    try {
                                        val response = calls.loginCall(username, password)
                                        val rsp = JSON.parse<LoginResponse>(response)

                                        when (rsp.error) {
                                            0 -> {
                                                console.log(rsp.message)

                                                val copyPastaPage = CopyPastaPage.create {
                                                    loginResponse = rsp
                                                    //groupResponse = groupRsp
                                                    cont = container
                                                }
                                                render(copyPastaPage, container)


                                                /*val groupPage = GroupPage.create {
                                                    loginResponse = rsp
                                                    cont = container
                                                }
                                                render(groupPage, container)*/
                                            }
                                            1, 2 -> {
                                                window.alert(rsp.message)
                                                password = ""
                                            }
                                            else -> {
                                                window.alert(rsp.message)
                                                password = ""
                                            }
                                        }
                                    } catch (e: Exception) {
                                        e.message?.let { it1 -> window.alert(it1) }
                                    }
                                }
                        }
                        +"Login"
                    }

                }

            }

        }

    }

}