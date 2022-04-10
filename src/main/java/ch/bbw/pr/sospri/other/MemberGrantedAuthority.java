package ch.bbw.pr.sospri.other;

import org.springframework.security.core.GrantedAuthority;

/**
 * @class: MemberGrantedAuthority
 * @author: Anes Hodza
 * @version: 08.04.2022
 **/

public class MemberGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 8835903531623403993L;
    private String authority;

    public MemberGrantedAuthority(String authority) {
        super();
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "MemberGrantedAuthority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
