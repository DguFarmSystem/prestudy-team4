// src/main/java/prestudy/team4/board/repository/CommentRepository.java
package prestudy.team4.board.repository;

import prestudy.team4.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByIdAsc(Long postId);
}
