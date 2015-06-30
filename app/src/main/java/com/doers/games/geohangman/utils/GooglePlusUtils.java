package com.doers.games.geohangman.utils;

import android.util.Log;

import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all basic operations for GooglePlus
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class GooglePlusUtils {

    /** Private Constructor * */
    private GooglePlusUtils() {
    }

    /**
     * This method retrieves a list of people data
     *
     * @param peopleData
     *
     * @return List of people data
     */
    public static List<UserInfo> retrievePeople(People.LoadPeopleResult peopleData) {
        List<UserInfo> people = null;

        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                people = new ArrayList<UserInfo>(count);
                for (Person person : personBuffer) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(person.getId());
                    userInfo.setName(person.getDisplayName());
                    people.add(userInfo);
                }
            } finally {
                personBuffer.close();
            }
        } else {
            Log.e(Messages.CIRCLES_PEOPLE_TAG, String.format(Messages.PERSON_FRIENDS_INFORMATION_ERROR, peopleData
                    .getStatus()));
        }

        return people;
    }
}
