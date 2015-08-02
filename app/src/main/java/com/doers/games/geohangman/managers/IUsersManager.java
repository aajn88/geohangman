package com.doers.games.geohangman.managers;

import com.doers.games.geohangman.model.UserInfo;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IUsersManager {

    /**
     * This method creates the user in this device. There will be just one user
     *
     * @param user User
     * @return User Id if success, otherwise returns null
     */
    boolean createUser(UserInfo user);

    /**
     * This method retrieves stored User Id
     * @return User if exists. Otherwise returns null
     */
    UserInfo getUser();

    /**
     * Creates a given token to the registered user
     *
     * @param token Token to be set
     * @return The token if success, otherwise returns null
     */
    boolean createToken(String token);

    /**
     * Retrieves registered token
     *
     * @return Registered token, otherwise returns null
     */
    String getToken();

}
