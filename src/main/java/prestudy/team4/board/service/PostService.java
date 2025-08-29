package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.domain.PostImage;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.exception.PostNotFoundException;
import prestudy.team4.board.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜젝션을 기본값으로 적용해두자.
@RequiredArgsConstructor // 생성자 주입
public class PostService { // 서비스: 비즈니스 로직 담당.
    private final PostRepository postRepository;

    // 게시글 작성 (C)
    @Transactional(readOnly = false)
    public PostResponseDto createPost(PostCreateDto postCreateDto) {
        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();

        // 이미지가 있으면 이미지를 업로드하자.
        if (postCreateDto.getImageKeys() != null) {
            postCreateDto.getImageKeys().forEach(key -> {
                PostImage postImage = new PostImage(key);
                post.addImage(postImage);
            });
        }
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    // 게시글 단일 조회 (R)
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findByIdWithImages(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없어요."));
        return new PostResponseDto(post);
    }

    // 게시글 목록 조회
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllWithImages().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 키 값을 바탕으로 엔티티를 가져오기.
    public Post getPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없어요."));
    }

    // 게시글 수정 (U)
    @Transactional(readOnly = false)
    public PostResponseDto updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post targetPost = postRepository.findByIdWithImages(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없어요."));

        targetPost.updateTitle(postUpdateDto.getTitle());
        targetPost.updateContent(postUpdateDto.getContent());
        targetPost.getImages().clear(); // 기존 이미지 목록을 비워요.

        if (postUpdateDto.getImageKeys() != null) { // 이미지 필드가 있다면 이미지 업로드를 진행해요.
            postUpdateDto.getImageKeys().forEach(key -> {
                PostImage newImage = new PostImage(key);
                targetPost.addImage(newImage);
            });
        }
        Post updatedPost = postRepository.save(targetPost);
        return new PostResponseDto(updatedPost);
    }

    // 게시글 삭제 (D)
    @Transactional(readOnly = false)
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없어요."));
        postRepository.deleteById(id);
    }
}
