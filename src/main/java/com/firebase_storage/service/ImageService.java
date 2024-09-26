package com.firebase_storage.service;

import com.firebase_storage.entity.Image;
import com.firebase_storage.entity.Task;
import com.firebase_storage.repository.ImageRepository;
import com.firebase_storage.repository.TaskRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final TaskRepository taskRepository;
    private final String BUCKET_NAME = "kanban-65318.appspot.com";

    public List<Image> addImagesToTask(List<MultipartFile> files, Integer taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }

        Task task = taskOptional.get();

        List<Image> savedImages = files.stream()
                .map(file -> {
                    try {
                        String imageUrl = uploadImageToFirebase(file);
                        Image image = new Image();
                        image.setImageUrl(imageUrl);
                        image.setTask(task);
                        return imageRepository.save(image);
                    } catch (IOException exception) {
                        throw new RuntimeException("Failed to upload image", exception);
                    }
                })
                .collect(Collectors.toList());

        return savedImages;
    }

    public String uploadImageToFirebase(MultipartFile file) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/serviceAccountKey.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        Blob blob = storage.create(blobInfo, file.getBytes());

        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, fileName);
    }
}
