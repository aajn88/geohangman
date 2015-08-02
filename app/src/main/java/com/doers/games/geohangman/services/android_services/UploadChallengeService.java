package com.doers.games.geohangman.services.android_services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.managers.IChallengesManager;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.services.IServerClientService;
import com.google.inject.Inject;

import java.util.List;

import roboguice.inject.InjectResource;
import roboguice.service.RoboService;

/**
 * This service uploads the whole challenge to the server. This includes challenge information and
 * pic
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class UploadChallengeService extends RoboService {

    /** Log Tag * */
    private static final String LOG_TAG = UploadChallengeService.class.getName();

    /** Upload Notification Id * */
    private static final Integer UPLOAD_NOTIFICATION_ID = 1;

    /** Default max progress for notification progress bar * */
    private static final Integer DEFAULT_MAX_PROGRESS = 100;

    /** Default start progress for notification progress bar * */
    private static final Integer DEFAULT_START_PROGRESS = 0;

    /** Uploading files notification title * */
    @InjectResource(R.string.upload_file_notification_title)
    String mNotificationTitle;

    /** Uploading files notification content * */
    @InjectResource(R.string.upload_file_notification_content)
    String mNotificationContent;

    /** Uploading files notification content * */
    @InjectResource(R.string.upload_file_notification_content_during_process)
    String mNotificationDuringProgress;

    /** Uploading files notification completed * */
    @InjectResource(R.string.upload_file_notification_completed)
    String mNotificationCompleted;

    /** Geohangman's Server client * */
    @Inject
    private IServerClientService clientService;

    /** Challenges Manager * */
    @Inject
    private IChallengesManager challengesManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new UploadChallengeAsyncTask().execute();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class UploadChallengeAsyncTask extends AsyncTask<Void, Void, Void> {

        /** This flag indicates if a given Android version is indeterminate or not * */
        private Boolean indeterminate;

        /** The Notification Builder * */
        private NotificationCompat.Builder mBuilder;

        /** The Notification Manager * */
        private NotificationManager mNotificationManager;

        public UploadChallengeAsyncTask() {
            indeterminate = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        }

        @Override
        protected void onPreExecute() {
            mBuilder = new NotificationCompat.Builder(UploadChallengeService.this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(mNotificationTitle)
                    .setContentText(mNotificationContent)
                    .setProgress(DEFAULT_MAX_PROGRESS, DEFAULT_START_PROGRESS, indeterminate)
                    .setOngoing(Boolean.TRUE);

            mNotificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(UPLOAD_NOTIFICATION_ID, mBuilder.build());
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Challenge> challenges = challengesManager.findAll();

            if (challenges == null || challenges.isEmpty()) {
                return null;
            }

            float increment = DEFAULT_MAX_PROGRESS / challenges.size();
            float currentProgress = DEFAULT_START_PROGRESS;

            for (Challenge challenge : challenges) {
                Log.d(LOG_TAG, String.format("Sending challenge to [userId = %s, imagePath = %s]",
                        challenge.getOpponentId(), challenge.getPicPath()));
                try {
                    int challengeId = challenge.getId();
                    challenge.setId(null);
                    clientService.createChallenge(challenge);
                    challengesManager.deleteById(challengeId);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "An error has occurred while sending challenge to server", e);
                }

                if (!indeterminate) {
                    currentProgress += increment;
                    int intProg = (int) currentProgress;
                    String progressMsg = String.format(mNotificationDuringProgress, intProg);
                    mBuilder.setContentText(progressMsg)
                            .setProgress(DEFAULT_MAX_PROGRESS, intProg, indeterminate);
                    mNotificationManager.notify(UPLOAD_NOTIFICATION_ID, mBuilder.build());
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mBuilder.setContentText(mNotificationCompleted)
                    // Removes progress bar
                    .setProgress(0, 0, indeterminate).setOngoing(Boolean.FALSE);
            mNotificationManager.notify(UPLOAD_NOTIFICATION_ID, mBuilder.build());
        }
    }
}
