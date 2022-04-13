package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
import ch.bbw.pr.sospri.other.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @class: MessageController
 * @author: Anes Hodza
 * @version: 10.04.2022
 **/

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    MemberService memberService;

    @GetMapping("/delete-message/{id}")
    public String deleteMessage(@PathVariable Long id, Principal principal) {
        if (memberService.getByUserName(principal.getName()).isSupervisor() || memberService.getByUserName(principal.getName()).isAdmin()) {
            messageService.deleteById(id);
        }
        return "redirect:/get-channel";
    }

    @PostMapping("/edit-message/{id}")
    public String editMessage(@PathVariable Long id, @ModelAttribute Message message, Principal principal) {
        System.out.println(memberService.getByUserName(principal.getName()).isAdmin());
        if (memberService.getByUserName(principal.getName()).isAdmin()) {
            messageService.update(id, message);
        }
        return "redirect:/get-channel";
    }
}
