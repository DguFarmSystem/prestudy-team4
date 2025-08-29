package prestudy.team4.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.domain.User;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    // 게시글 작성 (Post)
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PostCreateDto postCreateDto) {
        PostResponseDto postResponseDto = postService.createPost(user, postCreateDto);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    // 게시글 목록 조회 (Get)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // id를 기반으로 특정 게시글 가져오기 (Get)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.getPostById(id);
        return ResponseEntity.ok(postResponseDto);
    }

    // id를 기반으로 특정 게시글 수정하기 (Patch)
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PostUpdateDto postUpdateDto) {
        PostResponseDto postResponseDto = postService.updatePost(id, user, postUpdateDto);
        return ResponseEntity.ok(postResponseDto);
    }

    // id를 기반으로 특정 게시글 삭제하기 (Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        postService.deletePost(id, user);
        return ResponseEntity.noContent().build();
    }
}
