package com.doers.games.geohangman.controllers.challenger_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.inject.Inject;

import java.io.IOException;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;

/**
 * This class sends the Challenge through NFC to User's Opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@ContentView(R.layout.activity_send_challenge)
public class SendChallengeActivity extends RoboActionBarActivity {

    /** The GeoHangman Service * */
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Error while creating challenge message * */
    @InjectResource(R.string.error_creating_challenge)
    private String mErrorCreatingChallengeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recievedIntent = getIntent();
        UserInfo opponent = (UserInfo) recievedIntent
                .getSerializableExtra(Constants.SELECTED_OPPONENT_EXTRA);
        new SendChallengeAsyncTask(opponent.getId()).execute();
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

    private class SendChallengeAsyncTask extends AsyncTask<Void, Void, Boolean> {

        /** The opponent Id * */
        private String opponentId;

        /** Progress Dialog * */
        private ProgressDialog progress;

        /**
         * Constructor given an opponent Id
         *
         * @param opponentId The opponent Id
         */
        private SendChallengeAsyncTask(String opponentId) {
            this.opponentId = opponentId;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(SendChallengeActivity.this, "", Messages.LOADING);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = Boolean.TRUE;
            try {
                geoHangmanService.sendChallengeToOpponent(opponentId);
            } catch (IOException e) {
                Log.e(Messages.ERROR, mErrorCreatingChallengeMsg, e);
                success = Boolean.FALSE;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(SendChallengeActivity.this, R.string.internet_connection_error,
                        Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }
    }

}
