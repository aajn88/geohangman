package com.doers.games.geohangman.services;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IUsersManager {

    /**
     * This method creates the user in this device. There will be just one user
     *
     * @param id User Id
     * @return User Id if success, otherwise returns null
     */
    String createUser(String id);

    /**
     * This method retrieves stored User Id
     * @return User Id if exists. Otherwise returns null
     */
    String getUser();

    /**
     * Creates a given token to the registered user
     *
     * @param token Token to be set
     * @return The token if success, otherwise returns null
     */
    String createToken(String token);

    /**
     * Retrieves registered token
     *
     * @return Registered token, otherwise returns null
     */
    String getToken();

}
