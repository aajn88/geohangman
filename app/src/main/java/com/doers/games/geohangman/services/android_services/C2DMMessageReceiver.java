package com.doers.games.geohangman.services.android_services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.controllers.opponent_activities.StartChallengeActivity;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class C2DMMessageReceiver extends BroadcastReceiver {

    /** Challenger Id Key * */
    public static final String CHALLENGER_ID = "challengerId";

    /** Challenger Name Key * */
    public static final String CHALLENGER_NAME = "challengerName";

    /** Challenge Id Key * */
    public static final String CHALLENGE_ID = "challengeId";

    /** Notification Title Key * */
    public static final String NOTIFICATION_TITLE = "gcm.notification.title";

    /** Notification Body Key * */
    public static final String NOTIFICATION_BODY = "gcm.notification.body";

    /** Notification Id * */
    public static final Integer NOTIFICATION_ID = 1603;

    /** GCM Expected Action **/
    public static final String GCM_ACTION = "com.google.android.c2dm.intent.RECEIVE";

    /**
     * Manages on receive GCM Message
     *
     * @param context Current context
     * @param intent  Captured intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (GCM_ACTION.equals(action)) {
            Log.w(Messages.GCM_TAG, "Received message");
            Bundle data = intent.getExtras();
            Log.d(Messages.GCM_TAG,
                    String.format("%s = %s", CHALLENGER_ID, data.getString(CHALLENGER_ID)));
            Log.d(Messages.GCM_TAG,
                    String.format("%s = %s", CHALLENGER_NAME, data.getString(CHALLENGER_NAME)));
            Log.d(Messages.GCM_TAG,
                    String.format("%s = %s", CHALLENGE_ID, data.getString(CHALLENGE_ID)));

            if(data.getString(CHALLENGE_ID) != null) {
                createNotification(context, data);
            }
        }
    }

    /**
     * This method creates notification in UI and sets up pending intent for challenge
     *
     * @param context Current Context
     * @param data    Challenge Data
     */
    private void createNotification(Context context, Bundle data) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, StartChallengeActivity.class);
        intent.putExtras(data);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(data.getString(NOTIFICATION_TITLE))
                .setContentText(data.getString(NOTIFICATION_BODY)).setContentIntent(pendingIntent)
                .setAutoCancel(Boolean.TRUE);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
