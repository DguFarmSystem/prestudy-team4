package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prestudy.team4.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 메서드는 작성할 필요 X. JPA에서 기본 제공.

    // 게시글 목록을 조회할 때 이미지를 함께 가져오자.
    // Fetch Join; Post 엔티티 p를 조회할 때, 연관된 p.images 또한 DB에서 즉시 조회
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.images")
    List<Post> findAllWithImages();

    // 게시글 단일 조회 시 이미지를 함께 가져오자. (Fetch Join)
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.images WHERE p.postId = :id")
    Optional<Post> findByIdWithImages(@Param("id") Long id);
}
