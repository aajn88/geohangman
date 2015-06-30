package com.doers.games.geohangman.view;

import android.content.Context;
import android.view.ViewGroup;

/**
 *
 * This is the LetterButton Factory
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class LetterButtonFactory {

    /** Space Char for LetterButton **/
    static final Character SPACE_CHAR = ' ';

    /** Invalid Index for LetterButton **/
    static final Integer INVALID_INDEX = -1;

    /** Default width for LetterButton **/
    private static final Integer WIDTH_LETTER_BUTTON = 120;

    /** Private no-parameters constructor **/
    private LetterButtonFactory(){}

    /**
     * This is the LetterButton factory for an Empty LetterButton
     *
     * @param context Application context
     * @return a disabled LetterButton with a space as letter and -1 as its index
     */
    public static LetterButton createEmptyLetterButton(Context context) {
        return createLetterButton(context, SPACE_CHAR, INVALID_INDEX, Boolean.FALSE);
    }

    /**
     * Creates a LetterButton with an specific letter and index. Enabled by default
     *
     * @param context Application context
     * @param letter to be set in the LetterButton
     * @param index to be set in the LetterButton
     * @return Enabled LetterButton with given letter and index
     */
    public static LetterButton createLetterButton(Context context, Character letter, Integer index) {
        return createLetterButton(context, letter, index, Boolean.TRUE);
    }

    /**
     * Creates a LetterButton with an specific letter and index. Enabled by default
     *
     * @param context Application context
     * @param letter to be set in the LetterButton
     * @param index to be set in the LetterButton
     * @param enabled the LetterButton. True or False.
     * @return Enabled/Disabled LetterButton with given letter and index
     */
    public static LetterButton createLetterButton(Context context, Character letter, Integer index, Boolean enabled) {
        LetterButton letterButton = new LetterButton(context, letter, index);
        letterButton.setEnabled(enabled);
        letterButton.setLayoutParams(new ViewGroup.LayoutParams(WIDTH_LETTER_BUTTON, ViewGroup.LayoutParams.WRAP_CONTENT));
        return letterButton;
    }

}
