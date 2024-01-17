package br.com.tasks.core.domain.data.service

import br.com.tasks.core.domain.model.Task
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.data.response.SimpleResponse

interface TaskService {
    suspend fun getTasks(): List<Task>
    suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse
}