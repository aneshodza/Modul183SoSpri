package ch.bbw.pr.sospri.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A member
 * 
 * @author Peter Rutschmann
 * @version 09.04.2020
 */
@Entity
@Table(name = "member")
public class Member {
	@Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue=20)
	private Long id;
	
	@NotEmpty (message = "prename may not be empty" )
	@Size(min=2, max=512, message="Die Länge des Vornamens muss 2 bis 25 Zeichen sein.")
	private String prename;
	
	@NotEmpty (message = "lastname may not be empty" )
	@Size(min=2, max=20, message="Die Länge des Nachnamens 2 bis 25 Zeichen sein.")
	private String lastname;

	private String password;
	private String username;
	
	private String authority;

	public Member(String prename, String lastname, String password) {
		this.prename = prename;
		this.lastname = lastname;
		this.password = password;
		this.username = prename.toLowerCase() + "." + lastname.toLowerCase();
		this.authority = "member";
	}

	public Member() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", prename=" + prename + ", lastname=" + lastname + ", password=" + password
				+ ", username=" + username + ", authority=" + authority + "]";
	}

}
