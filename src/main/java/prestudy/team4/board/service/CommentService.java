// src/main/java/prestudy/team4/board/service/CommentService.java
package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.Comment;
import prestudy.team4.board.dto.CommentCreateDto;
import prestudy.team4.board.dto.CommentResponseDto;
import prestudy.team4.board.repository.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto create(Long postId, Long userId, CommentCreateDto req) {
        Comment saved = commentRepository.save(
                Comment.builder()
                        .postId(postId)
                        .userId(userId)
                        .content(req.content())
                        .build()
        );
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> listByPost(Long postId) {
        return commentRepository.findByPostIdOrderByIdAsc(postId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUserId().equals(userId)) {
            throw new SecurityException("본인 댓글만 삭제 가능합니다.");
        }
        commentRepository.delete(comment);
    }

    private CommentResponseDto toResponse(Comment c) {
        return new CommentResponseDto(
                c.getCommentId(),
                c.getPostId(),
                c.getUserId(),
                c.getContent(),
                c.getCreatedAt()
        );
    }
}
