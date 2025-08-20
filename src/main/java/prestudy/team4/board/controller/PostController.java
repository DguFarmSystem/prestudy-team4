package prestudy.team4.board.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 (Post)
    @PostMapping
    public PostResponseDto create(@Valid @RequestBody PostCreateDto postCreateDto) {
        return postService.createPost(postCreateDto);
    }

    // 게시글 목록 조회 (Get)
    @GetMapping
    public List<PostResponseDto> getAll() {
        return postService.getAllPosts();
    }

    // id를 기반으로 특정 게시글 가져오기 (Get)
    @GetMapping("/{id}")
    public PostResponseDto getById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // id를 기반으로 특정 게시글 수정하기 (Patch)
    @PatchMapping("/{id}")
    public PostResponseDto update(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return postService.updatePost(id, postUpdateDto);
    }

    // id를 기반으로 특정 게시글 삭제하기 (Delete)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
