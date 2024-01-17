package br.com.tasks.data.service

import br.com.tasks.core.domain.data.repository.TaskRepository
import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.core.domain.model.Task
import br.com.tasks.core.domain.usecase.ValidateCreateTaskRequest
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.data.request.toTask
import br.com.tasks.data.response.SimpleResponse

class TaskServiceImpl constructor(
    private val taskRepository: TaskRepository,
    private val validateCrateTaskRequest: ValidateCreateTaskRequest
) : TaskService {

    override suspend fun getTasks(): List<Task> {
        return taskRepository.getTasks()
    }

    override suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse {
        val result = validateCrateTaskRequest(createTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = "Preencha todos os campos")
        }

        val insert = taskRepository.insert(task = createTaskRequest.toTask())
        if (!insert) {
            return SimpleResponse(success = false, message = "Error ao cadastrar a task", statusCode = 400)
        }

        return SimpleResponse(success = true, message = "Tarefa cadastrada com sucesso", statusCode = 201)
    }
}