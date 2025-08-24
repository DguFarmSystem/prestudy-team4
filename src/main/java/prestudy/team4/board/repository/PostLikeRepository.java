// src/main/java/prestudy/team4/board/repository/PostLikeRepository.java
package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.PostLike;
import prestudy.team4.board.domain.PostLikeId;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    boolean existsById_PostIdAndId_UserId(Long postId, Long userId);
    long countById_PostId(Long postId);
}
