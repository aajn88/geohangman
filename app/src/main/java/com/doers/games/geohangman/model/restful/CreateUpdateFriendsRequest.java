package com.doers.games.geohangman.model.restful;

import com.doers.games.geohangman.model.UserInfo;

import java.util.List;

/**
 * Create/Update User's friends Request. This request does not include User Id because it is sent
 * as a path variable in URL
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class CreateUpdateFriendsRequest extends AbstractRequest {

    /** User's Friends **/
    private List<UserInfo> friends;

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
