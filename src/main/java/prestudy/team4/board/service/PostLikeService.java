// src/main/java/prestudy/team4/board/service/PostLikeService.java
package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.domain.PostLike;
import prestudy.team4.board.domain.UserEntity;
import prestudy.team4.board.repository.PostLikeRepository;
import prestudy.team4.board.repository.PostRepository;
import prestudy.team4.board.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void like(Long postId, Long userId) {
        // 이미 좋아요면 멱등 처리
        if (postLikeRepository.existsByPost_PostIdAndUser_UserId(postId, userId)) return;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + userId));

        try {
            postLikeRepository.save(PostLike.builder()
                    .post(post)
                    .user((User) user)
                    .build());
        } catch (DataIntegrityViolationException e) {
            // 동시성 상황에서 UNIQUE(post_id,user_id) 위반 → 멱등하게 무시
        }
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        postLikeRepository.deleteByPost_PostIdAndUser_UserId(postId, userId);
    }

    @Transactional(readOnly = true)
    public long count(Long postId) {
        return postLikeRepository.countByPost_PostId(postId);
    }

    @Transactional(readOnly = true)
    public boolean liked(Long postId, Long userId) {
        return userId != null && postLikeRepository.existsByPost_PostIdAndUser_UserId(postId, userId);
    }
}



