import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// @Repository: 이 인터페이스가 데이터 접근 계층(Repository)임을 나타냅니다.
// JpaRepository<Entity, IdType>: JPA 기능을 상속받아 사용합니다.
@Repository
public interface AuthRepository extends JpaRepository<User, String> {

    // 주어진 사용자 ID 또는 이메일이 이미 존재하는지 확인하는 메소드입니다.
    boolean existsByUserIdOrEmail(String userId, String email);
}
