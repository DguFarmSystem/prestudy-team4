package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; // post 고유 ID (Auto Increment)

    @ManyToOne(fetch = FetchType.LAZY) // 회원 : 게시글이 1 : N 관계이므로 게시글 : 회원은 N : 1 관계
    @JoinColumn(name = "userId", nullable = false)
    //private User user; // user 고유 ID (FK, 외래키)

    @Column(nullable = false, length = 150)
    private String title; // 제목 (NOT NULL, VARCHAR(150))

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용 (NOT NULL, TEXT)

    @Column
    private LocalDateTime deletedAt; // 삭제일시

    // 생성자 (새로운 글 등록 시 사용)
    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 글 수정을 위한 update 메서드 분리해두기
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 글 삭제를 실제 row를 날리지 않고, 삭제 일자만 기록하기 위해 메서드를 만들자.
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
