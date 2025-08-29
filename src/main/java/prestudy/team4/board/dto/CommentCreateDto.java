// src/main/java/prestudy/team4/board/dto/CommentCreateDto.java
package prestudy.team4.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateDto(
        @NotBlank
        @Size(max = 200)
        String content
) {}

