package site.sfire.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.util.*
import jdk.jshell.execution.Util
import kotlinx.html.*
import site.sfire.utils.Utils
import java.util.Calendar
import java.util.Date

fun Application.configureRouting() {
    routing {
        get("/api/changelog") {
            call.respondHtml {
                body {
                    h4 { +"Device changes:" }
                    ul {
                        Utils.getChangelogDevice().forEach {
                            li { +it }
                        }
                    }
                    h4 { +"Sources changes:" }
                    ul {
                        Utils.getChangelogSources().forEach {
                            li { +it }
                        }
                    }
                    p {
                        +"Latest build from HoloCtion. Update now to keep safe and improve device stability"
                    }
                    Utils.build?.let {
                        p { +Utils.buildDate!! }
                        p { +it.buildId }
                    }
                }
            }
        }
    }
}
