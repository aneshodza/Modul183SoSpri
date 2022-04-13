package ch.bbw.pr.sospri.other;

/**
 * @class: ProfanityFilter
 * @author: Anes Hodza
 * @version: 11.04.2022
 **/

public class ProfanityFilter {

    private static String[] wordList = new String[]{"fuck", "penis", "cunt", "faggot", "sex"};

    public static boolean hasProfanity(String sentence) {
        String[] s = sentence.split(" ");
        for(String word: ProfanityFilter.wordList) {
            for (String sentenceWord: s) {
                if (sentenceWord.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        }
        return false;
    }
}
