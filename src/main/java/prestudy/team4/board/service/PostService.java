package prestudy.team4.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.domain.PostImage;
import prestudy.team4.board.dto.PostCreateDto;
import prestudy.team4.board.dto.PostResponseDto;
import prestudy.team4.board.dto.PostUpdateDto;
import prestudy.team4.board.repository.PostRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜젝션을 기본값으로 적용해두자.
public class PostService { // 서비스: 비즈니스 로직 담당.
    private final PostRepository postRepository;

    // 생성자
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성 (C)
    @Transactional(readOnly = false)
    public PostResponseDto createPost(PostCreateDto postCreateDto, List<MultipartFile> images) {
        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();

        // 이미지가 있으면 이미지를 업로드하자.
        if (images != null && !images.isEmpty()) {
            for (MultipartFile imageFile : images) { // 각 이미지에 대해 반복
                /* try {
                    String imageUrl = imageUploader.upload(imageFile);
                    PostImage postImage = PostImage.builder()
                            .imageUrl(imageUrl)
                            .build();
                    post.addImage(postImage);
                } catch (IOException err) {
                    throw new RuntimeException("이미지 업로드에 실패했어요. ", err);
                } */
            }
        }
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    // 게시글 단일 조회 (R)
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findByIdWithImages(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
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
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
    }

    // 게시글 수정 (U)
    @Transactional(readOnly = false)
    public PostResponseDto updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post targetPost = postRepository.findByIdWithImages(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));

        if (postUpdateDto.getTitle() != null) {
            targetPost.updateTitle(postUpdateDto.getTitle());
        }
        if (postUpdateDto.getContent() != null) {
            targetPost.updateContent(postUpdateDto.getContent());
        }
        Post updatedPost = postRepository.save(targetPost);
        return new PostResponseDto(updatedPost);
    }

    // 게시글 삭제 (D) - softDelete!
    @Transactional(readOnly = false)
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없어요: " + id));
        post.softDelete();
    }
}
