package prestudy.team4.board.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
public class PostUpdateDto {
    @Size(max = 150, message = "제목은 150자를 초과할 수 없어요.") // VARCHAR(150)
    private String title;

    private String content;
}
