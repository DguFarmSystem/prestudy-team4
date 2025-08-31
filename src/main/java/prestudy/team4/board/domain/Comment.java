// src/main/java/prestudy/team4/board/domain/Comment.java
package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY) // 댓글(N) : 게시글(1) 관계
    @JoinColumn(name = "post_id", nullable = false) // Post 테이블의 post_id 컬럼과 매핑
    private Post post;
    
    @ManyToOne(fetch = FetchType.LAZY) // 댓글(N) : 사용자(1) 관계
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 user_id 컬럼과 매핑
    private User user;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 200)
    private String content;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
//만약 오류나면
//ALTER TABLE comments
//MODIFY commentId BIGINT NOT NULL AUTO_INCREMENT,
//ADD PRIMARY KEY (commentId);


