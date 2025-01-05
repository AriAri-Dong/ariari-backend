package com.ariari.ariari.commons.manager;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.ariari.ariari.commons.image.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
//@Component
public class S3Manager implements FileManager {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Manager(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    //s3에 이미지 저장하기
    @Override
    public String saveFile(MultipartFile image, String domain) {
        String fileName = UUID.randomUUID() + "_" + domain + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    // s3에서 이미지 제거하기(파일명)
    public void deleteImageByFileName(String fileName) {
        try{
            amazonS3.deleteObject(bucket, fileName);
        }catch (Exception e){
            throw new RuntimeException(); // 예외처리 수정 예정
        }
    }


    // s3에서 이미지 제거하기(경로)
    @Override
    public void deleteFile(String filePath){
        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/", bucket, amazonS3.getRegionName());
        checkValidFilePath(filePath, bucketUrlPrefix); // 형식검사
        String fileKey = filePath.substring(bucketUrlPrefix.length()); // 형식에 맞게 문자열 파싱
        deleteImageByFileName(fileKey);
    }

    // 경로가 형식에 맞는지 확인
    public void checkValidFilePath(String filePath, String bucketUrlPrefix){
        if (!filePath.startsWith(bucketUrlPrefix)) {throw new IllegalArgumentException("Invalid S3 public URL");}
    }

    //실제 이미지의 절대경로 Url
    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }


}
