package com.doers.games.geohangman.view;

import android.content.Context;
import android.widget.Button;

/**
 * This class is a Letter Representation in the view
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class LetterButton extends Button {

    /** Button letter **/
    private Character letter;

    /** Disordered Letter position **/
    private Integer index;

    public LetterButton(Context context, Character letter, Integer index) {
        super(context);
        setLetter(letter, index);
    }

    public Integer getIndex() {
        return index;
    }

    public Character getLetter() {
        return letter;
    }

    /**
     * This method sets the letter and the index of the button
     *
     * @param letter
     * @param index
     */
    public void setLetter(Character letter, Integer index) {
        this.letter = letter;
        this.index = index;
        this.setText(Character.toString(letter));
        setEnabled(index != LetterButtonFactory.INVALID_INDEX);
    }

    /**
     * This method sets the current instance as Empty
     */
    public void setEmpty() {
        setLetter(LetterButtonFactory.SPACE_CHAR, LetterButtonFactory.INVALID_INDEX);
    }

    public boolean isEmpty() {
        return index == LetterButtonFactory.INVALID_INDEX;
    }
}
