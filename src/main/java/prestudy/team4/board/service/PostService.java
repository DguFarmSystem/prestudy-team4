package prestudy.team4.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService { // 서비스: 비즈니스 로직 담당.
    private final PostRepository postRepository;

    // 생성자
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성 (C)
    // 이미지 링크들을 배열 형태로 받아서 포함해야 함.
    public PostResponseDto createPost(PostCreateDto postCreateDto) {
        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(
                savedPost.getPostId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );
    }

    // 게시글 단일 조회 (R)
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시글 목록 조회
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    // 키 값을 바탕으로 엔티티를 가져오기.
    public Post getPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
    }

    // 게시글 수정 (U)
    public PostResponseDto updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post targetPost = getPostEntityById(id);
        targetPost.update(postUpdateDto.getTitle(), postUpdateDto.getContent());
        Post updatedPost = postRepository.save(targetPost);
        return new PostResponseDto(
                updatedPost.getPostId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt()
        );
    }

    // 게시글 삭제 (D) - softDelete!
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
        post.softDelete();
    }
}
