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
import android.widget.ImageView;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import java.util.GregorianCalendar;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This is the Start Challenge Activity. This activity starts when the opponent has received the
 * challenge. Here the opponent has to guess the word.
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class StartChallengeActivity extends RoboActionBarActivity {

    /** GeoHangman main service * */
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Challenge Pic ImageView * */
    @InjectView(R.id.challengePicIv)
    private ImageView mChallengePicIv;

    @Inject
    private StartChallengeActivityHelper startChallengeHelper;

    /** Google Map * */
    private GoogleMap mMap;

    /** Last time Back button was pressed * */
    private GregorianCalendar lastBackPressed = null;

    private static final Long THRESHOLD = 1000l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_challenge);

        Intent challengeIntent = getIntent();
        handleIntent((Intent) challengeIntent.getParcelableExtra(Constants.CHALLENGE_EXTRA));
    }

    /**
     * This method handles NFC intent. Retrieves challenge information and send it to GeoHangman
     * main service to process the data.
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        NdefMessage challengeMsg = (NdefMessage) rawMsgs[0];
        NdefRecord[] records = challengeMsg.getRecords();

        byte[] image = records[0].getPayload();
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
     * This method sets up all challenge views: - Map - Image - Word
     */
    private void setUpChallengeViews() {
        mChallengePicIv.setImageBitmap(geoHangmanService.getStoredPic());
        setUpMap();
    }

    /**
     * This method sets up map with challenge location and zoom
     */
    private void setUpMap() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        }

        if (mMap != null) {
            mMap.clear();
            Challenge.MapPoint point = geoHangmanService.getStoredLocation();
            LatLng latLng = new LatLng(point.getLat(), point.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, point.getZoom()));
        }
    }

    @Override
    public void onBackPressed() {
        if (lastBackPressed != null && isValidLastBackPressed()) {
            geoHangmanService.restartAll();
            finish();
        } else {
            lastBackPressed = new GregorianCalendar();
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isValidLastBackPressed() {
        GregorianCalendar now = new GregorianCalendar();
        return now.getTimeInMillis() - lastBackPressed.getTimeInMillis() <= THRESHOLD;
    }

    /**
     * This is the Start Challenge Async Task. Its job is process incoming data and set up views
     */
    private class StartChallengeAsync extends AsyncTask<Void, Void, Void> {

        /** Progress Dialog while background service is in progress * */
        private ProgressDialog progress;

        /** Pic Challenge bytes * */
        private byte[] image;

        /** Challenge args * */
        private String challengeArgs;

        /**
         * This constructor receives challenge pic bytes and challengeArgs
         *
         * @param image         bytes
         * @param challengeArgs Using the following format: "(word)|(lat)|(lng)|(zoom)" ->
         *                      "MyWord|33.96482810963319|-118.30714412033558|6.589385"
         */
        public StartChallengeAsync(byte[] image, String challengeArgs) {
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
            startChallengeHelper.setUpWord();
            progress.dismiss();
        }
    }
}
