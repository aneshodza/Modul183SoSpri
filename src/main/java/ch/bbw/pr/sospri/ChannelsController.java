package ch.bbw.pr.sospri;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import ch.bbw.pr.sospri.other.ProfanityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ChannelsController
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class ChannelsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageService messageservice;
	@Autowired
	MemberService memberservice;

	@GetMapping("/get-channel")
	public String getRequestChannel(Model model, Principal principal) {
		if (memberservice.getByUserName(principal.getName()).shouldChange()) {
			logger.warn(principal.getName() + " has to change password");
			return "redirect:/pass-change";
		}
		model.addAttribute("messages", messageservice.getAll());
		Message message = new Message();
		message.setContent("Der zweite Pfeil trifft immer.");
		model.addAttribute("message", message);
		logger.trace(principal.getName() + " joined the chatroom");
		return "channel";
	}

	@PostMapping("/add-message")
	public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
		System.out.println("postRequestChannel(): message: " + message.toString());
		if (ProfanityFilter.hasProfanity(message.getContent())) {
			redirectAttributes.addAttribute("profanity", true);
			logger.warn(principal.getName() + " has posted profanity, deleting message");
			return "redirect:/get-channel";
		}
		if(bindingResult.hasErrors()) {
			System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
			model.addAttribute("messages", messageservice.getAll());
			logger.warn("An error was caused when posting");
			return "channel";
		}
		// Hack solange es kein authenticated member hat
		Member tmpMember = memberservice.getByUserName(principal.getName());
		message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
		message.setOrigin(new Date());
		System.out.println("message: " + message);
		logger.trace(principal.getName() + " posted a new message.", message);
		messageservice.add(message);
		
		return "redirect:/get-channel";
	}
}
