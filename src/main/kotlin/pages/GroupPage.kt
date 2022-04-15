package pages

import csstype.Color
import csstype.FontWeight
import csstype.px
import emotion.react.css
import http.Calls
import http.responses.GroupResponse
import http.responses.LoginResponse
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import react.*
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.label
import react.dom.render

external interface GroupProps : Props {
    var loginResponse: LoginResponse
    var cont: Element
}


val GroupPage = FC<GroupProps> { props ->
    val loginRsp: LoginResponse by useState(props.loginResponse)
    val container: Element by useState(props.cont)
    var groupRsp: GroupResponse by useState(GroupResponse(-1, "Not loaded", emptyArray()))

    val mainScope = MainScope()

    val calls = Calls(mainScope.coroutineContext)

    h1 {
        css {
            fontWeight = FontWeight.bold
            fontSize = 30.px
            color = Color("000000")
        }
        +"Group List"
    }

    useEffectOnce {

        mainScope.launch {

            val rsp = calls.groupCall(loginRsp)

            if (rsp.isNotEmpty()) {

                groupRsp = JSON.parse(rsp)

            }

        }

    }

    when (groupRsp.error) {

        0 -> {
            val groups = groupRsp.groups
            console.log(groups)

            if (groups.isNullOrEmpty()) {
                h1 {
                    css {
                        fontWeight = FontWeight.bold
                        fontSize = 30.px
                        color = Color("000000")
                    }
                    +"No Group found"
                }
            } else {

                for (group in groups) {

                    label {
                        css {
                            fontWeight = FontWeight.bold
                            fontSize = 20.px
                            color = Color("000000")
                        }
                        +group.name
                    }
                    br {}
                    label {
                        css {
                            fontWeight = FontWeight.normal
                            fontSize = 15.px
                            color = Color("000000")
                        }
                        +group.description
                    }
                    if (loginRsp.user.admin || (loginRsp.user.groupAdmin && loginRsp.user.IDgroup == group.IDgroup))
                        button {
                            css {
                                marginLeft = 20.px
                                fontSize = 15.px
                                backgroundColor = Color("fff000")
                                color = Color("000000")
                            }
                            type = ButtonType.button
                            onClick = {
                                mainScope.launch {
                                    val rsp = calls.deleteGroup(groupRsp, loginRsp, group)
                                    val deleteRsp = JSON.parse<GroupResponse>(rsp)
                                    console.log(deleteRsp)
                                }
                                /*val copyPastaPage = CopyPastaPage.create {
                                    loginResponse = loginRsp
                                    groupResponse = groupRsp
                                    cont = container
                                }
                                render(copyPastaPage, container)*/
                            }
                            +"Delete"
                        }
                    br {}
                    br {}

                }

            }

        }
        1, 2, 3, 4 -> {
            window.alert(groupRsp.message)
        }
        -1 -> {
            console.log(groupRsp.message)
        }
        else -> {
            window.alert(groupRsp.message)
        }

    }

}