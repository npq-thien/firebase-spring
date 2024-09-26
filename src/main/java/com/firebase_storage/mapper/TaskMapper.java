package com.firebase_storage.mapper;

import com.firebase_storage.dto.response.TaskResponse;
import com.firebase_storage.entity.Task;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

public class TaskMapper {
    public static TaskResponse taskToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .build();
    }
}
