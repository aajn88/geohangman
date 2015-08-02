package com.doers.games.geohangman.model;

import com.doers.games.geohangman.persistance.SerializableCollectionsType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.List;

/**
 * This is User's basic info
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class UserInfo implements Serializable {

    /** User's Google Id (GeoHangman Id) * */
    @DatabaseField(id = true)
    private String id;

    /** User's name * */
    @DatabaseField
    private String name;

    /** User's email * */
    @DatabaseField
    private String email;

    /** User's friends **/
    @DatabaseField(persisterClass = SerializableCollectionsType.class)
    private List<UserInfo> friends;

    /** User's GCM Token **/
    @DatabaseField(canBeNull = true)
    private String token;

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

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", friends=").append(friends);
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
