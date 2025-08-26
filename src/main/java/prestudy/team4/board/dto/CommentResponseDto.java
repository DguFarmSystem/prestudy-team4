// src/main/java/prestudy/team4/board/dto/CommentResponseDto.java
package prestudy.team4.board.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long postId,
        Long userId,
        String content,
        LocalDateTime createdAt
) {}
