package prestudy.team4.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prestudy.team4.board.dto.JoinDto;
import prestudy.team4.board.service.JoinService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class JoinController{

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinP(){
        return "joinP";
    }

    @PostMapping("/join")
    public String joinProcess(JoinDto joinDto){
        joinService.joinProcess(joinDto);
        return "redirect:/api/v1/auth/login";
    }
}
