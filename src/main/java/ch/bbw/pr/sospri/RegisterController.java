package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.other.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import javax.validation.Valid;
import java.security.SecureRandom;

/**
 * RegisterController
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class RegisterController {
	@Autowired
	MemberService memberservice;

	@GetMapping("/get-register")
	public String getRequestRegistMembers(Model model) {
		System.out.println("getRequestRegistMembers");
		model.addAttribute("registerMember", new RegisterMember());
		return "register";
	}

	@PostMapping("/get-register")
	public String postRequestRegistMembers(@Valid RegisterMember registerMember, BindingResult result, Model model) {
		System.out.println("postRequestRegistMembers: registerMember");
		System.out.println(registerMember);
		int validation = validateRegister(registerMember.getPrename(), registerMember.getLastname(), registerMember.getPassword(), registerMember.getConfirmation());
		if (validation == 0) {
			if (result.hasErrors()) {
				return "register";
			}
			CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder();
			String encodedPassword = customPasswordEncoder.encode(registerMember.getPassword());
			memberservice.add(new Member(registerMember.getPrename(), registerMember.getLastname(), encodedPassword));
			model.addAttribute("registerMember", registerMember);
			return "registerconfirmed";
		} else {
			String error = "";
			switch (validation) {
				case 1:
					error = "Password and confirmation are not same";
					break;
				case 2:
					error = "Username is already in use";
					break;
				case 3:
					error = "Firstname must be over 3 characters";
					break;
				case 4:
					error = "Lastname must be over 3 characters";
					break;
				case 5:
					error = "Your password does not meet the requirements";
					break;
				default:
					error = "There was an unexpected error...";
					break;
			}
			registerMember.setMessage(error);
			return "register";
		}
	}

	private int validateRegister(String prename, String lastname, String password, String confirmation) {
		if (!password.equals(confirmation)) {
			return 1;
		} else if (memberservice.usernameExists(prename + "." + lastname)) {
			return 2;
		} else if (prename.length() < 3) {
			return 3;
		} else if (lastname.length() < 3) {
			return 4;
		} else if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
			return 5;
		}
		return 0;
	}
}