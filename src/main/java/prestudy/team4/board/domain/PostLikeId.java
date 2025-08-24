// src/main/java/prestudy/team4/board/domain/PostLikeId.java
package prestudy.team4.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PostLikeId implements Serializable {

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
