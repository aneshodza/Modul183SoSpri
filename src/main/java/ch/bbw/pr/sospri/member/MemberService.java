package ch.bbw.pr.sospri.member;

import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.other.MemberToUserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

/**
 * MemberService
 * 
 * @author Peter Rutschmann
 * @version 09.04.2020
 * 
 * https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
 * https://reflectoring.io/spring-security-password-handli
 */
@Service
@Transactional
public class MemberService implements UserDetailsService{
	@Autowired
	private MemberRepository repository;
	
	public Iterable<Member> getAll(){
		return repository.findAll();
	}

	public void add(Member member) {
		repository.save(member);
	}

	public void update(Long id, Member member) {
		//save geht auch für update.
		repository.save(member);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Member getById(Long id) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getId() == id) {
				return member;
			}
		}
		System.out.println("MemberService:getById(), id does not exist in repository: " + id);
		return null;
	}
	
	public Member getByUserName(String username) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getUsername().equalsIgnoreCase(username)) {
				return member;
			}
		}
		System.out.println("MemberService:getByUserName(), username does not exist in repository: " + username);
		return null;
	}

	public boolean usernameExists(String username) {
		return StreamSupport.stream(getAll().spliterator(), false)
				.anyMatch(user -> username.equalsIgnoreCase(user.getUsername()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = repository.findMemberByUsername(username);
		return MemberToUserDetailsMapper.toUserDetails(member);
	}

	public void changePassword(String username, String password) {
		System.out.println(username);
		Member member = getByUserName(username);
		member.setPassword(password);
		member.setLastPassChange(LocalDate.now());
		repository.save(member);
	}
}
