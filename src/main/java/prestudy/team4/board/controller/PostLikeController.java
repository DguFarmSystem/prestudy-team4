// src/main/java/prestudy/team4/board/controller/PostLikeController.java
package prestudy.team4.board.controller;

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

    // ⚠️ 임시: 인증 안 붙였으므로 userId는 헤더에서 받음
    private Long getLoginUserId(String header) {
        if (header == null || header.isBlank()) throw new SecurityException("로그인 필요");
        return Long.parseLong(header);
        // 나중에 Spring Security / JWT 붙이면 여기 대체
    }

    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long postId,
                                     @RequestHeader(name = "X-USER-ID", required = false) String userHeader) {
        postLikeService.like(postId, getLoginUserId(userHeader));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long postId,
                                       @RequestHeader(name = "X-USER-ID", required = false) String userHeader) {
        postLikeService.unlike(postId, getLoginUserId(userHeader));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable Long postId) {
        return ResponseEntity.ok(postLikeService.count(postId));
    }
}
