package com.firebase_storage.controller;

import com.firebase_storage.dto.request.TaskCreateRequest;
import com.firebase_storage.dto.response.TaskResponse;
import com.firebase_storage.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping()
    ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request) {
        TaskResponse taskResponse = taskService.createTask(request);

        return ResponseEntity.ok(taskResponse);
    }
}
