package ch.bbw.pr.sospri.other;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

public class RandomCustomPasswordEncoder implements PasswordEncoder {
    private static Random r = new Random();
    private static String possibleChars = "1234567890";

    public String pepperGen(int length) { //Creates a random 2 char String, which acts as a pepper
        String pepper = "";

        for (int i = 0; i < length; i++) {
            pepper += RandomCustomPasswordEncoder.possibleChars.charAt(RandomCustomPasswordEncoder.r.nextInt(RandomCustomPasswordEncoder.possibleChars.length()));
        }
        System.out.println("The pepper: " + pepper);
        return pepper;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String pepper = pepperGen(2); //Creates a random 2 char pepper
        return BCrypt.hashpw(rawPassword.toString() + pepper, BCrypt.gensalt(8)); // This appends the pepper at the end of our password
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        for (int j = 0; j < RandomCustomPasswordEncoder.possibleChars.length(); j++) {
            for (int i = 0; i < RandomCustomPasswordEncoder.possibleChars.length(); i++) {
                System.out.println("Decoding inner loop with string [" + RandomCustomPasswordEncoder.possibleChars.charAt(j) + RandomCustomPasswordEncoder.possibleChars.charAt(i) + "]");
                if (BCrypt.checkpw(rawPassword.toString() + RandomCustomPasswordEncoder.possibleChars.charAt(j) + RandomCustomPasswordEncoder.possibleChars.charAt(i), encodedPassword)) {
                    System.out.println("The pepper is " + RandomCustomPasswordEncoder.possibleChars.charAt(j) + RandomCustomPasswordEncoder.possibleChars.charAt(i));
                    return true;
                }
            }
        }
        return false;
    }
}
