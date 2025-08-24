package prestudy.team4.board.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto {
    private final int status; // 에러 코드
    private final boolean success = false; // 실패에서만 사용하므로 항상 false
    private final String message; // 에러 메시지

    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
