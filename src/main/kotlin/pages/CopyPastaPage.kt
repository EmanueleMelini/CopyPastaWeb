package pages

import csstype.ClassName
import http.Calls
import http.responses.CopyPastaResponse
import http.responses.LoginResponse
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.render
import kotlin.random.Random

external interface CopyPastaProps : Props {
    var loginResponse: LoginResponse
    //var groupResponse: GroupResponse
    var cont: Element
}

val CopyPastaPage = FC<CopyPastaProps> { props ->
    val loginRsp: LoginResponse by useState(props.loginResponse)
    //val groupRsp: GroupResponse by useState(props.groupResponse)
    val container: Element by useState(props.cont)
    var copyPastaRsp: CopyPastaResponse by useState(CopyPastaResponse(-1, "Not loaded", emptyArray()))
    var filter: String? by useState(null)
    var rngFilter: Int? by useState(null)

    val mainScope = MainScope()

    val calls = Calls(mainScope.coroutineContext)

    useEffectOnce {

        mainScope.launch {

            val rsp = calls.copyCall(loginRsp)

            if (rsp.isNotEmpty())
                copyPastaRsp = JSON.parse(rsp)

        }

    }

    div {
        className = ClassName("row mt-3 mb-3")

        div {
            className = ClassName("col-10 offset-1")

            when (copyPastaRsp.error) {
                0 -> {
                    val copys = copyPastaRsp.copys

                    div {
                        className = ClassName("row mt-3")

                        div {
                            className = ClassName("col-2 offset-10")

                            button {
                                className = ClassName("btn btn-primary")
                                onClick = {
                                    val groupPage = GroupPage.create {
                                        loginResponse = loginRsp
                                        cont = container
                                    }
                                    render(groupPage, container)
                                }
                                +"Groups"
                            }

                        }

                    }

                    if (copys.isNullOrEmpty()) {
                        h1 {
                            className = ClassName("title mt-5")
                            +"No CopyPasta found"
                        }
                    } else {

                        val idList: ArrayList<Int> = ArrayList()
                        copys.forEach {
                            idList.add(it.IDcopy)
                        }

                        div {
                            className = ClassName("row mt-5")

                            div {
                                className = ClassName("col-xxl-4 col-xl-4 col-md-4 col-sm-12 col-12")

                                label {
                                    className = ClassName("title")
                                    +"CopyPasta List"
                                }

                            }

                            div {
                                className = ClassName("col-xxl-4 col-xl-4 col-md-4 col-sm-8 col-8 offset-xxl-2 offset-xl-2 offset-md-2 offset-sm-0 offset-0")

                                input {
                                    className = ClassName("form form-control")
                                    placeholder = "Search CopyPasta"
                                    id = "filter_text"
                                    value = filter
                                    onChange = {
                                        filter = it.target.value
                                        rngFilter = null
                                    }
                                }

                            }

                            div {
                                className = ClassName("col-xxl-1 col-xl-1 col-md-1 col-sm-2 col-2 offset-xxl-0 offset-xl-0 offset-md-0 offset-sm-0 offset-0")

                                button {
                                    className = ClassName("btn btn-primary")
                                    onClick = {
                                        filter = null
                                        (document.getElementById("filter_text") as HTMLInputElement).value = ""
                                        rngFilter = null
                                    }
                                    +"Reset"
                                }

                            }

                            div {
                                className = ClassName("col-xxl-1 col-xl-1 col-md-1 col-sm-2 col-2 offset-xxl-0 offset-xl-0 offset-md-0 offset-sm-0 offset-0")

                                button {
                                    className = ClassName("btn btn-primary")
                                    onClick = {
                                        filter = null
                                        (document.getElementById("filter_text") as HTMLInputElement).value = ""
                                        rngFilter = idList[Random.nextInt(idList.size)]
                                    }
                                    +"RNG"
                                }

                            }
                        }

                        var filteredCopys = copys

                        if (filter != null) {
                            filteredCopys = copys.filter {
                                it.title.lowercase().contains(filter!!.lowercase()) || it.body.lowercase().contains(filter!!.lowercase())
                            }.toTypedArray()
                        }

                        if (rngFilter != null) {
                            filteredCopys = copys.filter {
                                it.IDcopy == rngFilter
                            }.toTypedArray()
                        }

                        for (copy in filteredCopys) {

                            div {
                                className = ClassName("col-12 copy-card mt-3")
                                onClick = {
                                    window.navigator.clipboard.writeText(copy.body)
                                        .then {
                                            window.alert("CopyPasta copied to clipboard!")
                                        }
                                }

                                label {
                                    className = ClassName("copy-title mt-1")
                                    +copy.title
                                }
                                br {}
                                label {
                                    className = ClassName("copy-body mt-1 mb-1")
                                    +copy.body
                                }

                            }

                        }

                    }

                }
                1, 2, 3, 4 -> {
                    window.alert(copyPastaRsp.message)
                }
                -1 -> {
                    console.log(copyPastaRsp.message)
                }
                else -> {
                    window.alert(copyPastaRsp.message)
                }

            }

        }

    }

}