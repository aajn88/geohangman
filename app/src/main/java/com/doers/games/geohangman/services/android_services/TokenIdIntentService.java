package com.doers.games.geohangman.services.android_services;

import android.content.Intent;
import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.services.ITokenService;
import com.google.inject.Inject;

import java.io.IOException;

import roboguice.service.RoboIntentService;

/**
 * This class manages the device's token with Google Play Services
 */
public class TokenIdIntentService extends RoboIntentService {

    /** The Token Service * */
    @Inject
    private ITokenService tokenService;

    /**
     * Default constructor
     */
    public TokenIdIntentService() {
        super(TokenIdIntentService.class.getName());
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                tokenService.requestNewToken();
            } catch (IOException e) {
                Log.e(Messages.ERROR,
                        "An error has occurred while requesting a new Token to GCM servers", e);
            }
        }
    }

}
