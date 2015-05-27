package com.doers.games.geohangman.utils;

import java.util.Random;

/**
 *
 * StringUtils for basic GeoHangman String operations and constants
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class StringUtils {

    /** Separator used for challenge args **/
    public static final String SEPARATOR = "|";

    /** Regex Separator used for challenge args **/
    public static final String REGEX_SEPARATOR = "\\|";

    /** Private no-parameters constructor **/
    private StringUtils() {}

    /**
     *
     * This method disorders letter of a given word
     *
     * @param word to be disordered
     * @return disordered String
     */
    public static String disorderString(String word) {
        StringBuilder sb = new StringBuilder(word);

        Random ran = new Random();

        int s = word.length();
        for (int i = 1; i <= s - 1; i++) {
            int index = ran.nextInt(s - i) + i;
            swap(sb, i - 1, index);
        }

        return sb.toString();
    }

    /**
     * This method swaps chars at position i and j
     * @param sb where chars will be swap
     * @param i char at position i
     * @param j char at position j
     */
    private static void swap(StringBuilder sb, int i, int j) {
        Character c = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, c);
    }
}
