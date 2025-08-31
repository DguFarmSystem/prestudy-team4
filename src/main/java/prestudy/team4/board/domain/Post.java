package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; // post 고유 ID (Auto Increment)

    @ManyToOne(fetch = FetchType.LAZY) // 회원 : 게시글이 1 : N 관계이므로 게시글 : 회원은 N : 1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // user 고유 ID (FK, 외래키)

    @Column(nullable = false, length = 150)
    private String title; // 제목 (NOT NULL, VARCHAR(150))

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용 (NOT NULL, TEXT)

    @Column(nullable = false)
    private Long likeCount; // 좋아요 개수 (NOT NULL)

    @Column(nullable = false)
    private Long commentCount; // 댓글 개수 (NOT NULL)

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>(); // 이미지 연관관계 (양방향)

    @Version
    private Long version; // 동시에 좋아요를 누르는 상황 등에서 누락 제어를 위해 버전 필드 추가

    // 생성자 (새로운 글 등록 시 사용)
    @Builder
    public Post(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.likeCount = 0L;
        this.commentCount = 0L;
    }

    // 글 수정을 위한 update 메서드 분리해두기
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    // 좋아요, 댓글 개수 컬럼 증감을 위한 메서드들을 두자.
    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) this.likeCount--;
    }

    public void increaseComment() {
        this.commentCount++;
    }

    public void decreaseComment() {
        if (this.commentCount > 0) this.commentCount--;
    }

    // 이미지 추가 / 삭제
    public void addImage(PostImage image) {
        images.add(image);
        image.setPost(this);
    }

    public void removeImage(PostImage image) {
        images.remove(image);
        image.setPost(null);
    }
}
