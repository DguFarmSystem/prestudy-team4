package prestudy.team4.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            // JWT 토큰 기반에서는 클라이언트에서 토큰을 삭제하면 됩니다
            // 서버에서는 SecurityContext를 클리어합니다
            SecurityContextHolder.clearContext();

            return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "로그아웃 처리 중 오류가 발생했습니다."));
        }
    }
}