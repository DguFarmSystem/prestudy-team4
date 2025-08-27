package prestudy.team4.board.service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Uploader {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String editedFilename = dirName + "/" + UUID.randomUUID() + "-" + originalFilename;
            // 중복 파일명 이슈를 막기 위해 랜덤 UUID를 더해주기

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(editedFilename)
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(multipartFile.getBytes()));
            // s3에 이미지 업로드

            return "https://" + bucket + ".s3."
                    + s3Client.serviceClientConfiguration().region().id()
                    + ".amazonaws.com/" + editedFilename; // 이미지 업로드된 url 반환
        } catch (IOException e) { // 업로드 실패 시 예외처리
            throw new RuntimeException("S3 업로드에 실패했어요.", e);
        }
    }
}
