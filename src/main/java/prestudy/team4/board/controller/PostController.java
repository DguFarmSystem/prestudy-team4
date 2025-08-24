package prestudy.team4.board.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 (Post)
    // 이미지 파일과 게시글 데이터를 동시에 받기 위해서 multipart/form-data를 처리할 수 있게 하자.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponseDto create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestPart("postCreateDto") PostCreateDto postCreateDto,
            @RequestPart("images") List<MultipartFile> images
    ) {
        String username = userDetails.getUsername(); // email 등으로 바꿀 필요
        return postService.createPost(postCreateDto, images);
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
