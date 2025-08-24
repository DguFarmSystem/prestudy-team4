// GlobalExceptionHandler.java
package prestudy.team4.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prestudy.team4.board.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // PostNotFoundException 관리
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePostNotFoundException(PostNotFoundException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage() // 예외 메시지
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // IllegalArgumentException 관리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                ex.getMessage() // 예외 메시지
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}