package com.ariari.ariari.test.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TestModel {
    private MultipartFile image;
}
