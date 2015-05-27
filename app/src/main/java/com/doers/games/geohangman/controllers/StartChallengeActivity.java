package com.doers.games.geohangman.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.custom_components.LetterButton;
import com.doers.games.geohangman.custom_components.LetterButtonFactory;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.utils.ChallengeUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class StartChallengeActivity extends RoboActionBarActivity {

    /** GeoHangman main service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Challenge Pic ImageView **/
    @InjectView(R.id.challengePicIv)
    private ImageView mChallengePicIv;

    /** LinearLayout where final word will be placed **/
    @InjectView(R.id.finalWordLl)
    private LinearLayout mFinalWordLl;

    /** LinearLayout where disordered letters will be placed **/
    @InjectView(R.id.lettersLl)
    private LinearLayout mLettersLl;

    private WordViewManager wordViewManager;

    /** Google Map **/
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_challenge);

        Intent challengeIntent = getIntent();
        handleIntent((Intent)challengeIntent.getParcelableExtra(Constants.CHALLENGE_EXTRA));
    }

    /**
     * This method handles NFC intent. Retrieves challenge information and send it to
     * GeoHangman main service to process the data.
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Parcelable []rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        NdefMessage challengeMsg = (NdefMessage) rawMsgs[0];
        NdefRecord []records = challengeMsg.getRecords();

        byte []image = records[0].getPayload();
        String challengeArgs = new String(records[1].getPayload());

        new StartChallengeAsync(image, challengeArgs).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_challenge, menu);
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

    /**
     * This method sets up all challenge views:
     * - Map
     * - Image
     * - Word
     */
    private void setUpChallengeViews() {
        mChallengePicIv.setImageBitmap(geoHangmanService.getStoredPic());
        setUpMap();
        wordViewManager = new WordViewManager(geoHangmanService.getStoredWord());
    }

    /**
     * This method sets up map with challenge location and zoom
     */
    private void setUpMap() {
        if(mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        }

        if(mMap != null) {
            mMap.clear();
            Challenge.MapPoint point = geoHangmanService.getStoredLocation();
            LatLng latLng = new LatLng(point.getLat(), point.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, point.getZoom()));
        }
    }

    private class WordViewManager {

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

        public WordViewManager(String word) {
            setUpWord(word);
        }

        private void setUpWord(String word) {

            String disorderedWord = ChallengeUtils.disorderString(word);

            finalButtons = new LetterButton[disorderedWord.length()];
            lettersButtons = new LetterButton[disorderedWord.length()];

            mFinalWordLl.removeAllViews();
            mLettersLl.removeAllViews();

            for (int i = 0; i < disorderedWord.length(); i++) {
                final LetterButton emptyLetter = LetterButtonFactory.createEmptyLetterButton(StartChallengeActivity.this);
                finalButtons[i] = emptyLetter;
                mFinalWordLl.addView(emptyLetter);
                emptyLetter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeLetter(emptyLetter);
                    }
                });

                final LetterButton letterButton = LetterButtonFactory.
                        createLetterButton(StartChallengeActivity.this, disorderedWord.charAt(i), i);
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

                Toast.makeText(StartChallengeActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        private String buildFinalWord() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < finalButtons.length; i++) {
                sb.append(finalButtons[i].getLetter());
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
         * This method returns the next empty position
         *
         * @return next empty position
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
                pos = (currentEmptyPos >= finalButtons.length ? null : currentEmptyPos++);
            }

            return pos;
        }
    }

    /**
     * This is the Start Challenge Async Task. Its job is process incoming data and set up views
     */
    private class StartChallengeAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progress;
        private byte []image;
        private String challengeArgs;

        /**
         * This constructor receives challenge pic bytes and challengeArgs
         *
         * @param image bytes
         * @param challengeArgs Using the following format:
         *                      "(word)|(lat)|(lng)|(zoom)" -> "MyWord|33.96482810963319|-118.30714412033558|6.589385"
         */
        public StartChallengeAsync(byte []image, String challengeArgs) {
            this.image = image;
            this.challengeArgs = challengeArgs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(StartChallengeActivity.this, "", Messages.LOADING);
        }

        @Override
        protected Void doInBackground(Void... params) {
            geoHangmanService.startChallenge(image, challengeArgs);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUpChallengeViews();
            progress.dismiss();
        }
    }
}
