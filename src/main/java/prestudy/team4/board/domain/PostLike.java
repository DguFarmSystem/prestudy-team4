// src/main/java/prestudy/team4/board/domain/PostLike.java
package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_likes_post_user",
                columnNames = {"postId", "userId"}
        )
)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

}
