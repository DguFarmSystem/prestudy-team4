package prestudy.team4.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.config.JwtUtil;
import prestudy.team4.board.dto.LoginResponseDto;
import prestudy.team4.board.service.CustomerUserDetailService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailService userDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            // 사용자 인증
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // UserDetails 로드
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            // JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDto(token, username, "로그인 성공"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "잘못된 사용자명 또는 비밀번호입니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "로그인 처리 중 오류가 발생했습니다."));
        }
    }
}
