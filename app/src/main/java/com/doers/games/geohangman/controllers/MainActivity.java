package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IUsersService;
import com.doers.games.geohangman.utils.GooglePlusUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.inject.Inject;

import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This is the GeoHangman Main Activity.
 * <p/>
 * This class manages all G+ sign-in and profile information as User's Id and User's friends Id
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class MainActivity extends RoboActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    static {
        RoboGuice.setUseAnnotationDatabases(false);
    }

    /** G+ sign-in button * */
    @InjectView(R.id.signInButton)
    private SignInButton mSignInButton;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;
    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all issues preventing
     * sign-in without waiting.
     */
    private boolean mSignInClicked;
    /** Current Session User * */
    private UserInfo mCurrentUser;
    /** Geohangman Users Service * */
    @Inject
    private IUsersService usersService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGoogleApiClient.isConnecting()) {
                    mSignInClicked = true;
                    mGoogleApiClient.connect();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (currentPerson != null) {
            mCurrentUser = new UserInfo();
            mCurrentUser.setId(currentPerson.getId());
            mCurrentUser.setName(currentPerson.getDisplayName());
            mCurrentUser.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
        }
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && mSignInClicked && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        Log.d(Messages.CIRCLES_PEOPLE_TAG,
                String.format(Messages.RETRIEVING_FRIENDS_INFORMATION, mCurrentUser.getId(),
                        mCurrentUser.getName()));
        List<UserInfo> people = GooglePlusUtils.retrievePeople(peopleData);
        mCurrentUser.setFriends(people);

        Log.d(Messages.CIRCLES_PEOPLE_TAG, Messages.RETRIEVING_FRIENDS_INFORMATION_FINISH);

        new SendUser2ServerAsyncTask().execute();

        Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenuIntent);
    }

    /**
     * AsyncTask to send User to Server
     */
    private class SendUser2ServerAsyncTask extends AsyncTask<Void, Void, Boolean> {

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
        protected Boolean doInBackground(Void... params) {
            boolean successful = Boolean.TRUE;
            try {
                usersService.storeCurrentUser(mCurrentUser);
            } catch (IOException | NoSuchAlgorithmException | ResourceAccessException e) {
                Log.e(Messages.ERROR, Messages.SENDING_DATA_TO_SERVER_FAILED, e);
                successful = Boolean.FALSE;
            }
            return successful;
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            if (!successful) {
                Toast.makeText(MainActivity.this, R.string.internet_connection_error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}
