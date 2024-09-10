package com.archi_sub.archi_sub.config.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Service {
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.folder.photo}")
    private String photoFolder;

    //파일 삭제
    //파일 삭제
    public void deleteFile(String filePath){
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();
        s3Client.deleteObject(request);
    }

    //TODO 나중에 비동기 형태로 바꿔도 될듯
    public void deleteFiles(List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) {
            return; // 삭제할 파일이 없으면 메서드 종료
        }

        // 파일 키들을 ObjectIdentifier로 변환
        List<ObjectIdentifier> objectIdentifiers = filePaths.stream()
                .map(key -> ObjectIdentifier.builder().key(key).build())
                .collect(Collectors.toList());

        // 삭제 요청 빌드
        Delete delete = Delete.builder()
                .objects(objectIdentifiers)
                .build();

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(delete)
                .build();

        try {
            DeleteObjectsResponse response = s3Client.deleteObjects(deleteObjectsRequest);
            log.info("Successfully deleted {} objects.", response.deleted().size());

        } catch (S3Exception e) {
            log.error("Error deleting objects: {}", e.awsErrorDetails().errorMessage());
        }
    }
}
