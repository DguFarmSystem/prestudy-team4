// src/main/java/prestudy/team4/board/controller/PostLikeController.java
package prestudy.team4.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.service.PostLikeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    //  세션에서 USER_ID 꺼내기 (AuthController와 동일 흐름)
    private Long getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) throw new UnauthorizedException("로그인이 필요합니다.");
        Object v = session.getAttribute("USER_ID");
        if (v == null) throw new UnauthorizedException("로그인이 필요합니다.");
        if (v instanceof Number n) return n.longValue();
        try {
            return Long.valueOf(v.toString());
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("세션 사용자 식별값이 올바르지 않습니다.");
        }
    }

    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long postId,
                                     HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        postLikeService.like(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long postId,
                                       HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        postLikeService.unlike(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable Long postId) {
        return ResponseEntity.ok(postLikeService.count(postId));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String msg) { super(msg); }
    }
}
