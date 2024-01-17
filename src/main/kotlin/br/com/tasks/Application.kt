package br.com.tasks

import br.com.tasks.di.databaseModule
import br.com.tasks.di.repositoryModule
import br.com.tasks.di.serviceModule
import br.com.tasks.di.useCaseModule
import br.com.tasks.plugins.configureHTTP
import br.com.tasks.plugins.configureMonitoring
import br.com.tasks.plugins.configureRouting
import br.com.tasks.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    install(Koin) {
        modules(
            databaseModule,
            repositoryModule,
            serviceModule,
            useCaseModule
        )
    }

    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
