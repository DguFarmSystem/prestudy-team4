import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
으로 생성합니다.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
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
