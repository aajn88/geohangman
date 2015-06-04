package com.doers.games.geohangman.controllers;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.view.LetterButton;
import com.doers.games.geohangman.view.LetterButtonFactory;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.utils.StringUtils;
import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * This is the StartChallengeActivity Helper. This class manages all word views.
 * That means, this class manages all related with the word to be guessed by the opponent.
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@ContextSingleton
class StartChallengeActivityHelper {

    /** Array with all LetterButton that the User has selected **/
    private LetterButton[] finalButtons;

    /** Array with all LetterButton that the User can select **/
    private LetterButton[] lettersButtons;

    /** Current Empty position **/
    private Integer currentEmptyPos = 0;

    /** Flag to search again the next empty pos **/
    private Boolean searchAgain = Boolean.FALSE;

    /** Letter counter: To know how many letters has been selected **/
    private Integer letterCount = 0;

    /** LinearLayout where final word will be placed **/
    @InjectView(R.id.finalWordLl)
    private LinearLayout mFinalWordLl;

    /** LinearLayout where disordered letters will be placed **/
    @InjectView(R.id.lettersLl)
    private LinearLayout mLettersLl;

    /** GeoHangman main service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Context **/
    @Inject
    private Context context;

    /**
     * This method sets up the word to be guessed. This includes put all letters into a random position
     * and build all buttons
     */
    public void setUpWord() {

        String word = geoHangmanService.getStoredWord();
        String disorderedWord = StringUtils.disorderString(word);

        finalButtons = new LetterButton[disorderedWord.length()];
        lettersButtons = new LetterButton[disorderedWord.length()];

        mFinalWordLl.removeAllViews();
        mLettersLl.removeAllViews();

        for (int i = 0; i < disorderedWord.length(); i++) {
            final LetterButton emptyLetter = LetterButtonFactory.createEmptyLetterButton(context);
            finalButtons[i] = emptyLetter;
            mFinalWordLl.addView(emptyLetter);
            emptyLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeLetter(emptyLetter);
                }
            });

            final LetterButton letterButton = LetterButtonFactory.
                    createLetterButton(context, disorderedWord.charAt(i), i);
            lettersButtons[i] = letterButton;
            mLettersLl.addView(letterButton);
            letterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addLetter(letterButton);
                }
            });
        }
    }

    /**
     * This method adds a letter into final LetterButtons
     * @param letterButton to be added
     */
    private void addLetter(LetterButton letterButton) {
        Integer index = nextEmptyPosition();
        if(index == null) {
            return;
        }

        finalButtons[index].setLetter(letterButton.getLetter(), letterButton.getIndex());
        letterButton.setEmpty();
        letterCount++;

        if(letterCount == finalButtons.length) {
            String message = "Wrong Word! Try again!";
            if(geoHangmanService.verifyWord(buildFinalWord())) {
                message = "You got it! You win!";
            }

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method builds the final word.
     *
     * FIXME: There's a better way to do this
     *
     * @return the word selected by the opponent
     */
    private String buildFinalWord() {
        StringBuilder sb = new StringBuilder();
        for (LetterButton finalButton : finalButtons) {
            sb.append(finalButton.getLetter());
        }
        return sb.toString();
    }

    private void removeLetter(LetterButton letterButton) {
        lettersButtons[letterButton.getIndex()].setLetter(letterButton.getLetter(), letterButton.getIndex());
        letterButton.setEmpty();
        searchAgain = Boolean.TRUE;
        letterCount--;
    }

    /**
     * This method returns the next empty position.
     *
     *  FIXME: There's a better way to do this
     *
     * @return next empty position. Returns null if there's not any.
     */
    private Integer nextEmptyPosition() {
        Integer pos = null;
        if(searchAgain) {
            Boolean found = Boolean.FALSE;
            for (int i = 0; i < finalButtons.length && !found; i++) {
                if(finalButtons[i].isEmpty()) {
                    pos = currentEmptyPos = i;
                    found = true;
                }
            }
        } else {
            pos = (letterCount == finalButtons.length ? null : currentEmptyPos++);
        }

        return pos;
    }

}
