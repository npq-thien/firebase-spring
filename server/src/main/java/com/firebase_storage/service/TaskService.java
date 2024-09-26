package com.firebase_storage.service;

import com.firebase_storage.dto.request.TaskCreateRequest;
import com.firebase_storage.dto.response.TaskResponse;
import com.firebase_storage.entity.Task;
import com.firebase_storage.mapper.TaskMapper;
import com.firebase_storage.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        taskRepository.save(task);
        return TaskMapper.taskToTaskResponse(task);
    }

}
