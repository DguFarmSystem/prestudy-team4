// src/main/java/prestudy/team4/board/service/PostLikeService.java
package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.PostLike;
import prestudy.team4.board.repository.PostLikeRepository;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void like(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) return;
        try {
            postLikeRepository.save(PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build());
        } catch (DataIntegrityViolationException e) {
            // 동시 요청으로 UNIQUE 위반 시 멱등하게 무시 (정책에 따라 409로 바꿔도 됨)
        }
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
    }

    @Transactional(readOnly = true)
    public long count(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    public boolean liked(Long postId, Long userId) {
        return userId != null && postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }
}


