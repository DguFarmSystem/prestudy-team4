package prestudy.team4.board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostUpdateDto {
    @NotNull
    @Size(max = 150, message = "제목은 150자를 초과할 수 없어요.") // VARCHAR(150)
    private String title;

    @NotNull
    private String content;

    private List<String> imageKeys; // 업로드할 이미지들을 받아오자.
}
