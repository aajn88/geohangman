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

    /** Message when NFC error occurs sending files * */
    public static final String NFC_SEND_FILE_ERROR = "An NFC error has occurred while sending files";

    /** Message when is loading * */
    public static final String LOADING = "Loading...";

    /** Map Point Selected Tag * */
    public static final String MAP_POINT_SELECTED_TAG = "MAP_POINT_SELECTED";

    /** Circles People Tag * */
    public static final String CIRCLES_PEOPLE_TAG = "CIRCLES_PEOPLE_TAG";

    /** Map Point Selected Tag * */
    public static final String PERSON_FRIENDS_INFORMATION_ERROR =
            "Error requesting people data: " + "%s";

    /** Retrieving Friends Information * */
    public static final String RETRIEVING_FRIENDS_INFORMATION =
            "Retrieving friends " + "information for {Id = %s, Name = %s}";

    /** Retrieving friends information finished * */
    public static final java.lang.String RETRIEVING_FRIENDS_INFORMATION_FINISH = "Retrieving friends information process has finished";


    /** Private no-parameters constructor * */
    private Messages() {
    }
}
