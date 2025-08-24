package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

}
