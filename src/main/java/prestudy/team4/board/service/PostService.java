package prestudy.team4.board.service;

import org.springframework.stereotype.Service;
import prestudy.team4.board.repository.PostRepository;

@Service
public class PostService { // 서비스: 비즈니스 로직 담당.
    private final PostRepository postRepository;

    // 생성자
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성 (C)

    // 게시글 단일 조회 (R)

    // 게시글 목록 조회

    // 게시글 수정 (U)

    // 게시글 삭제 (D)

}
