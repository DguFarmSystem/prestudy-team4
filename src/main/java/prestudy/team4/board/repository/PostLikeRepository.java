// src/main/java/prestudy/team4/board/repository/PostLikeRepository.java
package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
