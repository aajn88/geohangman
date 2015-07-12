package com.doers.games.geohangman.services.android_services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * This is the Instance Id Listener
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class GeohangmanInstanceIdService extends InstanceIDListenerService {

    /**
     * If a token refresh is performed, then the TokenIdIntentService is called
     * Note: Token refresh is not processed in this class because of take advantage of RoboGuice
     * features
     */
    @Override
    public void onTokenRefresh() {
        Intent registerNewTokenIntent = new Intent(this, TokenIdIntentService.class);
        startService(registerNewTokenIntent);
    }
}
