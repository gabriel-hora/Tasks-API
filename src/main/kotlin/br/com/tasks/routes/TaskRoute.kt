package br.com.tasks.routes

import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.utils.Constants.TASK_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRoute(taskService: TaskService) {
    route(TASK_ROUTE) {
        getTasks(taskService)
        insertTask(taskService)
    }
}

private fun Route.getTasks(taskService: TaskService) {
    get {
        try {
            val tasks = taskService.getTasks()
            call.respond(HttpStatusCode.OK, tasks)

        } catch (e: Exception) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

private fun Route.insertTask(taskService: TaskService) {
    post {
        try {
            val request = call.receiveNullable<CreateTaskRequest>()
            request?.let {
                val simpleResponse = taskService.insert(it)
                when {
                    simpleResponse.success -> {
                        call.respond(HttpStatusCode.Created, simpleResponse)
                    }

                    simpleResponse.statusCode == 400 -> {
                        call.respond(HttpStatusCode.BadRequest, simpleResponse)
                    }
                }
            } ?: call.respond(HttpStatusCode.BadRequest, "requisição inválida")
        } catch (e: Exception) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}