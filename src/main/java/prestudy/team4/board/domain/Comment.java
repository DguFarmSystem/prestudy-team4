// src/main/java/prestudy/team4/board/domain/Comment.java
package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 임시: 나중에 Post, User 엔티티 완성되면 @ManyToOne으로 교체
    private Long postId;
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}
