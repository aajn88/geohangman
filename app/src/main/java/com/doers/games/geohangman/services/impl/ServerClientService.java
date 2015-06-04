package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.constants.ServerUrlTypes;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateUpdateUserRequest;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.utils.RestUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * IServerClientService implementation
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class ServerClientService implements IServerClientService {

    /** Properties File name * */
    private static final String PROPERTIES_FILE = "server.properties";

    /** Current context * */
    @Inject
    private Context context;

    /** ServerClientService properties * */
    private Properties properties;

    /**
     * Default constructor
     */
    public ServerClientService() {
        Log.d("Test", Boolean.toString(context == null));
    }

    /**
     * Get a configured properties instance
     *
     * @return Configured properties instance
     *
     * @throws IOException
     */
    private Properties getProperties() throws IOException {
        if (properties == null) {
            properties = ServerClientServiceHelper.buildProperties(context, PROPERTIES_FILE);
        }
        return properties;
    }

    /**
     * This method sends a user to the server to be created or updated
     *
     * @param user to be created/updated
     */
    @Override
    public void createOrUpdateUser(UserInfo user) throws NoSuchAlgorithmException, IOException {
        CreateUpdateUserRequest request = ServerClientServiceHelper.buildUserRequest(user,
                getProperties());

        String usersUrl = ServerClientServiceHelper.getUrl(getProperties(), ServerUrlTypes.USERS);

        request.getUser().setFriends(new ArrayList<UserInfo>(0));

        String response = RestUtils.post(usersUrl, request, String.class);

        Log.d(Messages.REQUEST_RESPONSE_TAG, String.format(Messages.SERVER_RESPONSE, response));

    }
}
