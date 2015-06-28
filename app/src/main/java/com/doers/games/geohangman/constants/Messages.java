package com.doers.games.geohangman.constants;

/**
 * GeoHangman Messages
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class Messages {

    /** Error Message * */
    public static final String ERROR = "Error";

    /** Fail to Load Message * */
    public static final String FAIL_TO_LOAD = "Failed to load";

    /** Message to the user to check his SD card * */
    public static final String CREATING_FILE_ERROR = "Can't create file to take picture!";

    /** Created Challenge Tag * */
    public static final String CREATED_CHALLENGE_TAG = "CREATED_CHALLENGE";

    /** Created Challenge Message * */
    public static final String CREATED_CHALLENGE_MSG = "The created Challenge: [ChallengeId = %s, ImageId = %s]";

    /** Message when is loading * */
    public static final String LOADING = "Loading...";

    /** Map Point Selected Tag * */
    public static final String MAP_POINT_SELECTED_TAG = "MAP_POINT_SELECTED";

    /** Circles People Tag * */
    public static final String CIRCLES_PEOPLE_TAG = "CIRCLES_PEOPLE_TAG";

    /** Map Point Selected Tag * */
    public static final String PERSON_FRIENDS_INFORMATION_ERROR = "Error requesting people data: %s";

    /** Retrieving Friends Information * */
    public static final String RETRIEVING_FRIENDS_INFORMATION = "Retrieving friends information for {Id = %s, Name = %s}";

    /** Retrieving friends information finished * */
    public static final String RETRIEVING_FRIENDS_INFORMATION_FINISH = "Retrieving friends information process has finished";

    /** Error while sending data to server * */
    public static final String SENDING_DATA_TO_SERVER_FAILED = "An error has occurred while sending data to server";

    /** Response to Geohangman request * */
    public static final String REQUEST_RESPONSE_TAG = "REQUEST_RESPONSE_TAG";

    /** Server response * */
    public static final String SERVER_RESPONSE = "Server response: %s";

    /** Tag for logs when Registered Friends * */
    public static final String REGISTERED_FRIENDS_TAG = "REGISTERED_FRIENDS";


    /** Private no-parameters constructor * */
    private Messages() {
    }
}
