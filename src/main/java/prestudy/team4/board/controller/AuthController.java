package prestudy.team4.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prestudy.team4.board.dto.JoinRequest;
import prestudy.team4.board.dto.LoginRequest;
import prestudy.team4.board.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequest request) {
        try {
            authService.join(request);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            authService.login(request);
            return ResponseEntity.ok("로그인이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }
}
