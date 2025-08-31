// src/main/java/prestudy/team4/board/service/CommentService.java
package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.Comment;
import prestudy.team4.board.domain.Post;
import prestudy.team4.board.domain.UserEntity;
import prestudy.team4.board.dto.CommentCreateDto;
import prestudy.team4.board.dto.CommentResponseDto;
import prestudy.team4.board.repository.CommentRepository;
import prestudy.team4.board.repository.PostRepository;
import prestudy.team4.board.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto create(Long postId, Long userId, CommentCreateDto req) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 작성할 게시글이 존재하지 않습니다."));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Comment saved = commentRepository.save(
                Comment.builder()
                        .post(post)          // ✅ 연관 엔티티로 설정
                        .user(user)          // ✅ 연관 엔티티로 설정
                        .content(req.content())
                        .build()
        );
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> listByPost(Long postId) {
        return commentRepository.findByPost_PostIdOrderByCommentIdAsc(postId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getById(Long commentId) {
        Comment c = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        return toResponse(c);
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUser().getUsername().equals(userId)) {
            throw new SecurityException("본인 댓글만 삭제 가능합니다.");
        }
        commentRepository.delete(comment);
        // 또는 레포 메서드 사용: commentRepository.deleteByCommentIdAndUser_UserId(commentId, userId);
    }

    private CommentResponseDto toResponse(Comment c) {
        return new CommentResponseDto(
                c.getCommentId(),
                c.getPost().getPostId(),
                c.getUser().getUsername(),
                c.getContent(),
                c.getCreatedAt()
        );
    }
}

