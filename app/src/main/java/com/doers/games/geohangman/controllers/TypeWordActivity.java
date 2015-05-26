package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.inject.Inject;

import java.util.regex.Pattern;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This class controls when User types the word to be guessed
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class TypeWordActivity extends RoboActionBarActivity {

    /** Regex that will be used to validate typed word **/
    private static final String VALIDATE_WORD_REGEX = "[A-Za-z]+";

    /** Compiled Pattern to be used **/
    private Pattern pattern = Pattern.compile(VALIDATE_WORD_REGEX);

    @InjectView(R.id.sendChallengeBtn)
    private Button mSendChallengeBtn;

    /** EditText where word is typed **/
    @InjectView(R.id.typedWordEt)
    private EditText mTypedWordEt;

    /** GeoHangman Main Service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_word);

        mTypedWordEt.setText(geoHangmanService.getStoredWord());
        mSendChallengeBtn.setEnabled(validateWord(geoHangmanService.getStoredWord()));

        mTypedWordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                processString(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSendChallengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSendChallengeActivity();
            }
        });
    }

    /**
     * This method starts SendChallengeActivity
     */
    private void startSendChallengeActivity() {
        Intent sendChallengeIntent = new Intent(this, SendChallengeActivity.class);
        startActivity(sendChallengeIntent);
    }

    /**
     * This method processes the string to know if is valid.
     * If is not valid, then next button is disabled and a Toast message is displayed
     *
     * @param s String to be validated
     */
    private void processString(String s) {
        boolean isValid = validateWord(s);
        mSendChallengeBtn.setEnabled(isValid);

        if (s.length() != 0 && !isValid) {
            Toast.makeText(TypeWordActivity.this, R.string.word_constraint, Toast.LENGTH_SHORT).show();
        }

        geoHangmanService.storeWord(s);
    }

    /**
     * This method validates if a given string has the following regex format:
     *
     * [A-Za-z]+
     *
     * @param s to be validated
     * @return True if is valid, otherwise returns False
     */
    private boolean validateWord(String s) {
        return (s != null ? pattern.matcher(s).matches() : Boolean.FALSE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_type_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
