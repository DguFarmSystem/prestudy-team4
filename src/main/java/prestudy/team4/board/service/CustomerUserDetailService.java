package prestudy.team4.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prestudy.team4.board.domain.UserEntity;
import prestudy.team4.board.dto.CustomUserDetails;
import prestudy.team4.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new CustomUserDetails(userEntity);
        }

        return null;
    }

}