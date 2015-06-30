package com.doers.games.geohangman.controllers.challenger_activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.services.IUsersService;
import com.google.inject.Inject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * This activity shows a list of registered user's friends which he can send a challenge
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class SelectOpponentActivity extends RoboActionBarActivity {

    /** The Users Services * */
    @Inject
    private IUsersService usersService;

    /** The GeoHangman Main Services * */
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Yes text * */
    @InjectResource(R.string.yes_text)
    private String mYesText;

    /** No text * */
    @InjectResource(R.string.no_text)
    private String mNoText;

    /** Sending Challenge Alert Dialog title * */
    @InjectResource(R.string.sending_challenge_opponent_alert_dialog_title)
    private String mSendingChallengeAlertTitle;

    /** Sending Challenge Alert Dialog message * */
    @InjectResource(R.string.sending_challenge_opponent_alert_dialog_msg)
    private String mSendingChallengeAlertMsg;

    /** Sending Challenge Alert Dialog message * */
    @InjectResource(R.string.error_loading_user_friends)
    private String mErrorLoadingUserFriends;

    /** The users List View * */
    @InjectView(R.id.usersLv)
    private ListView mUsersLv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_opponent);

        new LoadUserFriendsAsyncTask().execute();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_opponent, menu);
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
     * This method initiates SendChallengeActivity to send the challenge to a given opponent (a
     * User's friend)
     */
    private void sendChallengeToOpponent(UserInfo selectedFriend) {
        Intent sendChallengeIntent = new Intent(this, SendChallengeActivity.class);
        sendChallengeIntent.putExtra(Constants.SELECTED_OPPONENT_EXTRA, (Serializable) selectedFriend);
        startActivity(sendChallengeIntent);
    }

    /**
     * This Async Task loads all User Friends information and display their information
     */
    private class LoadUserFriendsAsyncTask extends AsyncTask<Void, Void, List<UserInfo>> {

        /** Progress Dialog * */
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(SelectOpponentActivity.this, "", Messages.LOADING);
        }

        /**
         * Override this method to perform a computation on a background thread. The specified
         * parameters are the parameters passed to {@link #execute} by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates on the UI thread.
         *
         * @param params The parameters of the task.
         *
         * @return A result, defined by the subclass of this task.
         *
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<UserInfo> doInBackground(Void... params) {

            List<UserInfo> friends = null;

            try {
                friends = usersService.getRegisteredFriends(usersService.getCurrentUser().getId());

                Log.d(Messages.REGISTERED_FRIENDS_TAG, friends.toString());

            } catch (IOException e) {
                Log.e(Messages.ERROR, mErrorLoadingUserFriends, e);
            }

            return friends;
        }

        @Override
        protected void onPostExecute(final List<UserInfo> friends) {
            if (friends != null) {
                final UsersListAdapter adapter = new UsersListAdapter(SelectOpponentActivity
                        .this, friends, usersService);
                mUsersLv.setAdapter(adapter);
                mUsersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        final UserInfo selectedFriend = friends.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                SelectOpponentActivity.this);
                        builder.setTitle(mSendingChallengeAlertTitle);
                        builder.setInverseBackgroundForced(Boolean.TRUE);
                        builder.setMessage(
                                String.format(mSendingChallengeAlertMsg, selectedFriend.getName()));
                        builder.setNegativeButton(mNoText, null);
                        builder.setPositiveButton(mYesText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendChallengeToOpponent(selectedFriend);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            } else {
                Toast.makeText(SelectOpponentActivity.this, R.string.error_loading_user_friends,
                        Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }
    }


}
