// src/main/java/prestudy/team4/board/controller/CommentController.java
package prestudy.team4.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.dto.CommentCreateDto;
import prestudy.team4.board.dto.CommentResponseDto;
import prestudy.team4.board.service.CommentService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    // ⚠️ 임시: 로그인 안 붙였으니 userId는 헤더에서 직접 받기
    private Long getLoginUserId(String header) {
        if (header == null || header.isBlank()) throw new SecurityException("로그인 필요");
        return Long.parseLong(header);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> create(
            @PathVariable Long postId,
            @RequestHeader(name = "X-USER-ID", required = false) String userHeader,
            @Valid @RequestBody CommentCreateDto request
    ) {
        CommentResponseDto res = commentService.create(postId, getLoginUserId(userHeader), request);
        return ResponseEntity.created(URI.create("/comments/" + res.id())).body(res);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> list(@PathVariable Long postId) {
        return commentService.listByPost(postId);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @RequestHeader(name = "X-USER-ID", required = false) String userHeader
    ) {
        commentService.delete(commentId, getLoginUserId(userHeader));
        return ResponseEntity.noContent().build();
    }
}
