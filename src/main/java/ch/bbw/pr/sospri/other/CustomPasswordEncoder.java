package ch.bbw.pr.sospri.other;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

/**
 * @class: CustomPasswordEncoder
 * @author: Anes Hodza
 * @version: 10.04.2022
 **/

public class CustomPasswordEncoder implements PasswordEncoder {

    //private static String pepper = "Th1515mys3cuR3P3pp3r";
    private static String pepper = "";

    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString() + pepper, BCrypt.gensalt(10));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString() + pepper, encodedPassword);
    }

}

