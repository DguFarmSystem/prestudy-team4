// src/main/java/prestudy/team4/board/dto/CommentCreateDto.java
package prestudy.team4.board.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDto(
        @NotBlank String content
) {}
