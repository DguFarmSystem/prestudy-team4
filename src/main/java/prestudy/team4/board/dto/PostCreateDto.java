package prestudy.team4.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
public class PostCreateDto {
    @NotBlank(message = "제목은 비워둘 수 없어요.") // NOT NULL
    @Size(max = 150, message = "제목은 150자를 초과할 수 없어요.") // VARCHAR(150)
    private String title;

    @NotBlank(message = "내용은 비워둘 수 없어요.") // NOT NULL
    private String content;
}
