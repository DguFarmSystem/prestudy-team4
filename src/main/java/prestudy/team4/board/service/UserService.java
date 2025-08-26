package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prestudy.team4.board.domain.User;
import prestudy.team4.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        userRepository.delete(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
