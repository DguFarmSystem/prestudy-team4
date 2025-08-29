package prestudy.team4.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prestudy.team4.board.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    // 이메일 기반 조회 및 중복 체크
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);

    // userid(username) 기반 조회 및 중복 체크
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);

    // PK(userId)로 조회
    UserEntity findByUserId(Integer userId);
}
