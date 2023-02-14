package site.sfire

import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import site.sfire.plugins.*
import site.sfire.utils.Utils

fun main() {
    Utils
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@OptIn(ExperimentalSerializationApi::class)
fun Application.module() {
    configureRouting()
    configureSerialization()
}
