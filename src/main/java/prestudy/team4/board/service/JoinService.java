package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prestudy.team4.board.domain.UserEntity;
import prestudy.team4.board.dto.JoinDto;
import prestudy.team4.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JoinService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto){

        // 이메일 중복 체크
        boolean isEmailExists = userRepository.existsByEmail(joinDto.getEmail());
        if(isEmailExists){
            return; // 또는 예외 처리
        }

        // userid 중복 체크
        boolean isUseridExists = userRepository.existsByUsername(joinDto.getUserid());
        if(isUseridExists){
            return; // 또는 예외 처리
        }

        UserEntity user = UserEntity.builder()
                .username(joinDto.getUserid())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .name(joinDto.getName())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }
}