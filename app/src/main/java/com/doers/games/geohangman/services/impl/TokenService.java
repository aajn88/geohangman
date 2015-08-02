package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.util.Log;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.services.ITokenService;
import com.doers.games.geohangman.managers.IUsersManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.inject.Inject;

import java.io.IOException;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectResource;

/**
 * This class manages app tokens requested from GCM servers
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@ContextSingleton
public class TokenService implements ITokenService {

    /** File where Geohangman token will be stored * */
    private static final String TOKEN_STORAGE_FILE = "geohangman.ghm";

    /** Current context * */
    @Inject
    private Context context;

    /** Default Sender Id * */
    @InjectResource(R.string.gcm_defaultSenderId)
    private String gcmSenderId;

    /** The users manager * */
    @Inject
    private IUsersManager usersManager;

    /** The Server Client Service * */
    @Inject
    private IServerClientService serverClientService;

    /**
     * This method returns the stored GCM Token. Returns null if it does not exist
     *
     * @return token
     */
    @Override
    public String getToken() throws IOException {

        return usersManager.getToken();
    }

    /**
     * This method requests a new Token from GCM servers.
     *
     * @return requested new token1
     */
    @Override
    public String requestNewToken() throws IOException {
        String token = requestToken();

        notifyTokenToServer(token);

        return token;
    }

    /**
     * This method notifies the server about new token
     *
     * @param token token to be notified to server
     */
    private void notifyTokenToServer(String token) throws IOException {
        boolean success = usersManager.createToken(token);

        if (success) {
            UserInfo user = usersManager.getUser();
            serverClientService.createOrUpdateToken(user.getId(), token);
        }
    }

    /**
     * This method requests a new token from Google servers
     *
     * @return New token
     *
     * @throws IOException
     */
    private String requestToken() throws IOException {
        InstanceID instanceID = InstanceID.getInstance(context);
        String token = "";
        try {
            token = instanceID.getToken(gcmSenderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            Log.e(Messages.ERROR, "An error has occurred while requesting token to GCM servers", e);
        }
        return token;
    }
    
}
