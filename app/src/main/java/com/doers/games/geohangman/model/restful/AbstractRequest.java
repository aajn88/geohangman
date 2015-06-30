package com.doers.games.geohangman.model.restful;

/**
 * Basic request properties
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public abstract class AbstractRequest {

    /** Security key that must be sent for authentication * */
    private String securityKey;

    /** Specifies if the request is a test * */
    private Boolean test;

    /**
     * @return the securityKey
     */
    public String getSecurityKey() {
        return securityKey;
    }

    /**
     * @param securityKey the securityKey to set
     */
    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    /**
     * @return the test
     */
    public Boolean getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(Boolean test) {
        this.test = test;
    }
}
