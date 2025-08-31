package prestudy.team4.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostUpdateDto {
    @NotBlank(message = "제목은 비워둘 수 없어요.") // NOT NULL
    @Size(max = 150, message = "제목은 150자를 초과할 수 없어요.") // VARCHAR(150)
    private String title;

    @NotBlank(message = "제목은 비워둘 수 없어요.") // NOT NULL
    private String content;

    private List<String> imageKeys; // 업로드할 이미지들을 받아오자.
}
