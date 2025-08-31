package prestudy.team4.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prestudy.team4.board.dto.S3UrlDto;
import prestudy.team4.board.service.S3Service;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping(value = "/put-url")
    public ResponseEntity<S3UrlDto> getPutS3Url (@RequestParam String filename) {
        //Long userId = user.getId();
        S3UrlDto s3UrlDto = s3Service.getPutS3Url(filename);
        return ResponseEntity.ok(s3UrlDto);
    }

    @GetMapping(value = "/get-url")
    public ResponseEntity<S3UrlDto> getGetS3Url(@RequestParam String key) {
        S3UrlDto s3UrlDto = s3Service.getGetS3Url(key);
        return ResponseEntity.ok(s3UrlDto);
    }
}
