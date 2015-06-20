package com.doers.games.geohangman.controllers.challenger_activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IUsersService;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;
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

    /** The users List View **/
    @InjectView(R.id.usersLv)
    private ListView mUsersLv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_opponent);

        new LoadUserFriendsAsyncTask(usersService.getCurrentUser()).execute();
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
     * This Async Task loads all User Friends information and display their information
     */
    private class LoadUserFriendsAsyncTask extends AsyncTask<Void, Void, List<UserInfo>> {

        /** Current User * */
        private UserInfo currentUser;

        /** Progress Dialog * */
        private ProgressDialog progress;

        /**
         * Constructor with current user in session
         *
         * @param currentUser The current user in session
         */
        public LoadUserFriendsAsyncTask(UserInfo currentUser) {
            this.currentUser = currentUser;
        }

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
                friends = usersService
                        .getRegisteredFriends(usersService.getCurrentUser().getId());

                Log.d("REGISTERED_FRIENDS", friends.toString());

            } catch (IOException e) {
                Log.e(Messages.ERROR, "Error retrieving registered friends", e);
            }

            return friends;
        }

        @Override
        protected void onPostExecute(List<UserInfo> friends) {
            if(friends != null) {
                final UsersListAdapter adapter = new UsersListAdapter(SelectOpponentActivity
                        .this, friends, usersService);
                mUsersLv.setAdapter(adapter);
            } else {
                Toast.makeText(SelectOpponentActivity.this, "An error has occurred while loading " +
                        "user's friends", Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }
    }
}
