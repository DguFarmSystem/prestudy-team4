// src/main/java/prestudy/team4/board/controller/CommentController.java
package prestudy.team4.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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

    //  세션에서 USER_ID 꺼내기
    private Long getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        Object val = session.getAttribute("USER_ID");
        if (val == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        if (val instanceof Number n) {
            return n.longValue();
        }
        throw new UnauthorizedException("세션 사용자 식별값이 올바르지 않습니다.");
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateDto requestDto,
            HttpServletRequest httpRequest,
            UriComponentsBuilder uriBuilder
    ) {
        Long userId = getLoginUserId(httpRequest);
        CommentResponseDto res = commentService.create(postId, userId, requestDto);

        URI location = uriBuilder
                .path("/api/v1/comments/{id}")
                .buildAndExpand(res.commentId())
                .toUri();

        return ResponseEntity.created(location).body(res);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> list(@PathVariable Long postId) {
        return commentService.listByPost(postId);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            HttpServletRequest httpRequest
    ) {
        Long userId = getLoginUserId(httpRequest);
        commentService.delete(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    // 401 처리를 위한 간단한 런타임 예외
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String msg) { super(msg); }
    }
}
