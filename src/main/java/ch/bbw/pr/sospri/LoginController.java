package ch.bbw.pr.sospri;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * @class: LoginController
 * @author: Anes Hodza
 * @version: 06.04.2022
 **/

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginDone() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }
}
