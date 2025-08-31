// src/main/java/prestudy/team4/board/repository/PostLikeRepository.java
package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPost_PostIdAndUser_UserId(Long postId, Long userId);

    long countByPost_PostId(Long postId);

    void deleteByPost_PostIdAndUser_UserId(Long postId, Long userId);
}
