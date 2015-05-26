package com.doers.games.geohangman.controllers;

import android.app.ProgressDialog;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;

/**
 * This class sends the Challenge through NFC to User's Opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class SendChallengeActivity extends RoboActionBarActivity {

    /** NFC Adapter **/
    private NfcAdapter mNfcAdapter;

    @Inject
    private IGeoHangmanService geoHangmanService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_challenge);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        new BuildNdefAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_challenge, menu);
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

    private class BuildNdefAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(SendChallengeActivity.this, "", Messages.LOADING);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mNfcAdapter.setNdefPushMessage(geoHangmanService.buildNdefMessage(), SendChallengeActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }

}
