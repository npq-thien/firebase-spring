package com.firebase_storage.mapper;

import com.firebase_storage.dto.response.ImageResponse;
import com.firebase_storage.entity.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//@Data
//@NoArgsConstructor
public class ImageMapper {
    public static ImageResponse imageToImageResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .taskId(image.getTask().getId())
                .build();
    }
}
