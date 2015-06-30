package com.doers.games.geohangman.model.restful;

import com.doers.games.geohangman.model.UserInfo;

/**
 * User Request
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class CreateUpdateUserRequest extends AbstractRequest {
    /** User to be created/updated **/
    private UserInfo user;

    /**
     * @return the user
     */
    public UserInfo getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(UserInfo user) {
        this.user = user;
    }
}
