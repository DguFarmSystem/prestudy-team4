package prestudy.team4.board.dto;

import lombok.*;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.domain.PostImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<String> imageKeys;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.imageKeys = post.getImages().stream()
                .map(PostImage::getImageKey)
                .collect(Collectors.toList());
    }
}
