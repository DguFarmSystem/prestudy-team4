import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;
import prestudy.team4.dto.UserRegistrationRequest; // DTO 임포트 경로에 따라 수정

@Service // 이 클래스가 비즈니스 로직을 담당하는 서비스 계층임을 나타냅니다.
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository; // Repository 계층 주입
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화 도구 주입

    /**
     * 새로운 사용자를 등록하는 메소드입니다.
     * @param request 회원가입 요청 DTO
     * @throws Exception 중복된 사용자 ID 또는 이메일이 존재할 경우 예외 발생
     */
    public void registerNewUser(UserRegistrationRequest request) throws Exception {
        // 1. 사용자 ID 또는 이메일 중복 확인
        if (authRepository.existsByUserIdOrEmail(request.getUserId(), request.getEmail())) {
            throw new Exception("이미 존재하는 사용자 ID 또는 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. DTO를 엔티티로 변환
        User newUser = new User(
                request.getUserId(),
                encodedPassword,
                request.getEmail(),
                request.getUserNn()
        );

        // 4. 데이터베이스에 사용자 저장
        authRepository.save(newUser);
    }
}
