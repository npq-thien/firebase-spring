package com.firebase_storage.controller;

import com.firebase_storage.dto.response.ImageResponse;
import com.firebase_storage.mapper.ImageMapper;
import com.firebase_storage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;


    @PostMapping("/upload/{taskId}")
    public ResponseEntity<List<ImageResponse>> uploadImage(@PathVariable Integer taskId, @RequestParam("files") List<MultipartFile> files) {
        try {
            List<ImageResponse> images = imageService.addImagesToTask(files, taskId)
                    .stream().map(ImageMapper::imageToImageResponse).toList();

            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Task not found
        }
    }
}
