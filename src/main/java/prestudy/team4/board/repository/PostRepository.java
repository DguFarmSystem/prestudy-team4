package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 메서드는 작성할 필요 X. JPA에서 기본 제공.
}
