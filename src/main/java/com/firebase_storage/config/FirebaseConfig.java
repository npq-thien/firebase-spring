package com.firebase_storage.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        //GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/serviceAccountKey.json"))
        //        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        //Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("kanban-65318.appspot.com") // Your Firebase bucket URL
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
