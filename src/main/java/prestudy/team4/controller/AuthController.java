import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
class UserRegistrationRequest {
    @NotBlank(message = "사용자 ID는 필수 입력값입니다.")
    @Size(min = 4, max = 100, message = "사용자 ID는 4자 이상, 100자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 100, message = "닉네임은 2자 이상, 100자 이하로 입력해주세요.")
    private String userNn;
}

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // Service 계층을 주입받습니다.

    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {

            authService.registerNewUser(request);

            return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
