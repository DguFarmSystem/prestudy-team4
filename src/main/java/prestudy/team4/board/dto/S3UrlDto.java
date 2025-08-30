package prestudy.team4.board.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class S3UrlDto { // GET/PUT 요청용 presigned url과 url을 발급받은 key를 전달
    private String preSignedUrl;
    private String key;

    @Builder
    public S3UrlDto(String preSignedUrl, String key) {
        this.preSignedUrl = preSignedUrl;
        this.key = key;
    }
}
