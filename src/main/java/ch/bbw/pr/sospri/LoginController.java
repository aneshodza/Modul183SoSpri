package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import ch.bbw.pr.sospri.other.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;

/**
 * @class: LoginController
 * @author: Anes Hodza
 * @version: 06.04.2022
 **/

@Controller
public class LoginController {

    @Autowired
    MemberService memberService;

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

    @GetMapping("/pass-change")
    public String passChange(Model model, Principal principal) {
        model.addAttribute("registerMember", new RegisterMember(Long.toString(Duration.between(LocalDate.now().atStartOfDay(), memberService.getByUserName(principal.getName()).getLastPassChange().atStartOfDay()).toDays() + 30)));
        return "/passChange";
    }

    @PostMapping("/pass-change")
    public String passChangeDone(RegisterMember registerMember, Principal principal, RedirectAttributes redirectAttributes) {
        if (registerMember.getPassword() == null || registerMember.getConfirmation() == null || !registerMember.getPassword().equals(registerMember.getConfirmation())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/pass-change";
        }
        CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder();
        String encodedPassword = customPasswordEncoder.encode(registerMember.getPassword());
        System.out.println(principal.getName());
        memberService.changePassword(principal.getName(), encodedPassword);
        redirectAttributes.addAttribute("changedPass", true);
        return "redirect:/index.html";
    }
}
