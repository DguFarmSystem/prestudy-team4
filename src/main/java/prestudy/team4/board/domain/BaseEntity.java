package prestudy.team4.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate // 생성될 때 생성일시 기록
    @Column(updatable = false, nullable = false) // 생성일자는 수정 불가! NOT NULL
    private LocalDateTime createdAt;

    @LastModifiedDate // 변경될 때 변경일시 기록
    @Column(nullable = false) // NOT NULL
    private LocalDateTime updatedAt;
}
