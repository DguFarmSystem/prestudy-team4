package prestudy.team4.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String email;
    private String password;
    private String name;
}

