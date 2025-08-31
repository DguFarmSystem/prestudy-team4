package prestudy.team4.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class LogoutController{

    @GetMapping("/logout")
    public String logoutPage(){
        return "logout";
    }
}