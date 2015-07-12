package com.doers.games.geohangman.services;

import java.io.IOException;

/**
 * This Interface represents GCM Token services
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface ITokenService {

    /**
     * This method returns the stored GCM Token. Returns null if it does not exist
     * @return token
     */
    String getToken() throws IOException;

    /**
     * This method requests a new Token from GCM servers.
     *
     * @return requested new token1
     */
    String requestNewToken() throws IOException;

}
