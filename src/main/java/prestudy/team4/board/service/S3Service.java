package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.dto.S3UrlDto;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket; // 버킷명 저장

    public S3UrlDto getPutS3Url(String filename) {
        // 이미지를 업로드할 때 s3의 PUT 주소를 반환해주자.
        String key = "posts/" + UUID.randomUUID() + "/" + filename;
        // 파일이름 설정하기 (posts/랜덤Id/{파일명})
        // userId 및 postId 포함하도록 바꿀 필요성 존재

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60)) // url 유효시간 1시간으로 설정
                .putObjectRequest(putObjectRequest)
                .build();

        // PUT 요청을 위한 presigned URL 생성
        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(presignRequest);
        String url = presignedPutObjectRequest.url().toExternalForm();

        return S3UrlDto.builder()
                .preSignedUrl(url)
                .key(key)
                .build();
    }

    public S3UrlDto getGetS3Url(String key) {
        // 이미지를 조회할 때 s3에 저장된 파일을 찾아 GET 주소를 반환해주자.
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60)) // url 유효시간 1시간으로 설정
                .getObjectRequest(getObjectRequest)
                .build();

        // GET 요청을 위한 presigned URL 생성
        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
        String url = presignedGetObjectRequest.url().toExternalForm();

        return S3UrlDto.builder()
                .preSignedUrl(url)
                .key(key)
                .build();
    }
}