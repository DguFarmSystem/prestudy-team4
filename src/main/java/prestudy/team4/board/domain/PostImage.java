package prestudy.team4.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId; // 이미지 ID (Auto increment)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 외래키 (postId). 게시글 한 개 당 이미지 N개 관계.

    @Column(nullable = false)
    private String imageKey; // 이미지 s3 key를 문자열로 저장. (NOT NULL)

    @Builder
    public PostImage(String imageKey) { // 생성자
        this.imageKey = imageKey;
    }

    protected void setPost(Post post) { // Post 설정용 setter
        this.post = post;
    }
}