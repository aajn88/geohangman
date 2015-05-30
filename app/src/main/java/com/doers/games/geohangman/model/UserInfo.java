package com.doers.games.geohangman.model;

import java.util.List;

/**
 * This is User's basic info
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class UserInfo {

    /** User's Google Id (GeoHangman Id) * */
    private String id;

    /** User's name * */
    private String name;

    /** User's email * */
    private String email;

    /** User's friends **/
    private List<UserInfo> friends;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the friends
     */
    public List<UserInfo> getFriends() {
        return friends;
    }

    /**
     * @return friends the friends to set
     */
    public void setFriends(List<UserInfo> friends) {
        this.friends = friends;
    }
}
