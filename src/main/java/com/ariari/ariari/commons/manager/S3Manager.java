package com.ariari.ariari.commons.manager;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ariari.ariari.commons.manager.file.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class S3Manager implements FileManager {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.cloudfrontdomain}")
    private String cloudFrontDomain;

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
    public void deleteFile(String filePath) {
        // CloudFront URL prefix 사용
        String cloudFrontUrlPrefix = String.format("https://%s/", cloudFrontDomain);
        checkValidFilePath(filePath, cloudFrontUrlPrefix); // URL 형식 검사
        // CloudFront URL에서 파일 키(파일명) 추출
        String fileKey = filePath.substring(cloudFrontUrlPrefix.length());
        deleteImageByFileName(fileKey); // 실제 S3에 저장된 파일 삭제
    }

    // URL 형식 검증 (CloudFront URL 기준)
    private void checkValidFilePath(String filePath, String urlPrefix) {
        if (!filePath.startsWith(urlPrefix)) {
            throw new IllegalArgumentException("Invalid CloudFront URL");
        }
    }

    // 파일의 Public URL 생성 (CloudFront URL 사용)
    private String getPublicUrl(String fileName) {
        return String.format("https://%s/%s", cloudFrontDomain, fileName);
    }


}
