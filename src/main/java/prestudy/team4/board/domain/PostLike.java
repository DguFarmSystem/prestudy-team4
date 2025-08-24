// src/main/java/prestudy/team4/board/domain/PostLike.java
package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "post_likes")
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
