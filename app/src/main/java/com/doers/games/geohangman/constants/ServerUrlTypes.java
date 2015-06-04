package com.doers.games.geohangman.constants;

/**
 * This enum classifies Server URL Types. That means, all different URLs that GeoHangman Server has
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public enum ServerUrlTypes {

    /** Default Server URL * */
    DEFAULT("server.url"),

    /** Users URL * */
    USERS("users.url"),

    /** Challenges URL * */
    CHALLENGES("challenges.url");

    private final String property;

    ServerUrlTypes(String property) {
        this.property = property;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }
}
