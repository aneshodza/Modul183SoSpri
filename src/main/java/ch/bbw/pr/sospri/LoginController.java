package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import ch.bbw.pr.sospri.other.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

/**
 * @class: LoginController
 * @author: Anes Hodza
 * @version: 06.04.2022
 **/

@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberService memberService;

    @GetMapping("/loginSuccess")
    public String getLoginInfo() {
        return "redirect:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginDone(Principal principal) {
        logger.trace(principal.getName() + " logged in");
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(Principal principal) {
        logger.trace(principal.getName() + " logged out");
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
            logger.warn("Passchange failed");
            return "redirect:/pass-change";
        }
        CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder();
        String encodedPassword = customPasswordEncoder.encode(registerMember.getPassword());
        System.out.println(principal.getName());
        logger.info(principal.getName() + " changed password");
        memberService.changePassword(principal.getName(), encodedPassword);
        redirectAttributes.addAttribute("changedPass", true);
        return "redirect:/index.html";
    }
}
