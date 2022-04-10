package ch.bbw.pr.sospri.other;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

/**
 * @class: MemberToUserDetailsMapper
 * @author: Anes Hodza
 * @version: 08.04.2022
 **/

public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;

        if (member != null) {
            java.util.Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            user = new User(member.getPrename() + "." + member.getLastname()
                    , member.getPassword()
                    , true
                    , true
                    , true
                    , true
                    ,authorities
            );
        }
        return user;
    }
}
