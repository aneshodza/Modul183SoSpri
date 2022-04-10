package ch.bbw.pr.sospri.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * To regist a new Member
 * @author peter.rutschmann
 * @version 27.04.2020
 */
public class RegisterMember {

	private MemberService memberService;
	@NotEmpty(message = "prename may not be empty")
	@Size(min=3, max=512, message="Die Länge des Vornamens muss 2 bis 25 Zeichen sein.")
	private String prename;
	@NotEmpty (message = "lastname may not be empty" )
	@Size(min=3, max=20, message="Die Länge des Nachnamens muss 2 bis 25 Zeichen sein.")
	private String lastname;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message="Passwort muss einen Grossbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen haben. Es muss auch 8 Zeichen lang sein")
	private String password;
	private String confirmation;
	private String message;

	public RegisterMember(String message) {
		this.message = message;
	}

	public RegisterMember() {
		this.message = "";
	}

	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isMessagePositive() {
		if (Double.parseDouble(this.getMessage()) > 0) return true;
			else return false;
	}

	@Override
	public String toString() {
		return "RegisterMember [prename=" + prename + ", lastname=" + lastname + ", password=" + password
				+ ", confirmation=" + confirmation + "]";
	}
}
