import kotlinx.browser.document
import pages.LoginPage
import react.create
import react.dom.render

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val login = LoginPage.create {
        username = ""
        password = ""
        cont = container
    }
    render(login, container)

}