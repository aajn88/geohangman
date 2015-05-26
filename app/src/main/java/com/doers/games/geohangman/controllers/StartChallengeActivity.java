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

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;

public class StartChallengeActivity extends RoboActionBarActivity {

    /** GeoHangman main service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

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

    private class StartChallengeAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progress;
        private byte []image;
        private String challengeArgs;

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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
}
