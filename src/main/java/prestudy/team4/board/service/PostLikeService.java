// src/main/java/prestudy/team4/board/service/PostLikeService.java
package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.PostLike;
import prestudy.team4.board.domain.PostLikeId;
import prestudy.team4.board.repository.PostLikeRepository;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    /**
     * 멱등(여러 번 요청해도 결과 동일): 이미 눌렀으면 조용히 통과
     */
    @Transactional
    public void like(Long postId, Long userId) {
        PostLikeId id = new PostLikeId(postId, userId);
        if (postLikeRepository.existsById_PostIdAndId_UserId(postId, userId)) return;

        // createdAt 제거 (BaseEntity가 자동 처리)
        postLikeRepository.save(PostLike.builder()
                .id(id)
                .build());
    }

    /**
     * 없으면 조용히 통과
     */
    @Transactional
    public void unlike(Long postId, Long userId) {
        postLikeRepository.deleteById(new PostLikeId(postId, userId));
    }

    @Transactional(readOnly = true)
    public long count(Long postId) {
        return postLikeRepository.countById_PostId(postId);
    }

    @Transactional(readOnly = true)
    public boolean liked(Long postId, Long userId) {
        if (userId == null) return false;
        return postLikeRepository.existsById_PostIdAndId_UserId(postId, userId);
    }
}

