package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prestudy.team4.board.domain.User;
import prestudy.team4.board.dto.JoinRequest;
import prestudy.team4.board.dto.LoginRequest;
import prestudy.team4.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(JoinRequest request) {
        // 이메일 중복 검사
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
                });

        // 비밀번호 암호화 후 사용자 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                    }
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
    }
}
