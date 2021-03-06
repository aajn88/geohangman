package com.doers.games.geohangman.services.impl;

import android.content.Context;
import android.util.Base64;

import com.doers.games.geohangman.constants.ServerUrlTypes;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.model.restful.CreateChallengeImageRequest;
import com.doers.games.geohangman.model.restful.CreateChallengeRequest;
import com.doers.games.geohangman.model.restful.CreateUpdateFriendsRequest;
import com.doers.games.geohangman.model.restful.CreateUpdateUserRequest;
import com.doers.games.geohangman.model.MapPoint;
import com.doers.games.geohangman.utils.ImageUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

/**
 * Helper class for ServiceClientService
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
final class ServerClientServiceHelper {

    /** Secure Key * */
    private static final String SECURE_KEY = "secure.key";

    /** Browser Key * */
    private static final String BROWSER_KEY = "browser.key";

    /** Secure Param 1 * */
    private static final String PARAM1 = "secure.param1";

    /** Secure Param 2 * */
    private static final String PARAM2 = "secure.param2";

    /** Separator used for codec * */
    private static final String SEPARATOR = ":";

    /** Algorithm * */
    private static final String ALGORITHM = "SHA-1";

    /** Properties File Charset * */
    private static final String PROPERTIES_FILE_CHARSET = "UTF-8";

    /** Properties File name * */
    private static final String PROPERTIES_FILE = "server.properties";

    /** ServerClientService properties * */
    private Properties properties;

    /** Private default constructor * */
    @Inject
    private ServerClientServiceHelper(Context context) throws IOException {
        this.properties = buildProperties(context, PROPERTIES_FILE);
    }

    /**
     * This method builds a CreateChallengeRequest given a Challenge. This challenge request does
     * not include the challenge image.
     *
     * @param challenge The given challenge to create its request
     *
     * @return Challenge request
     */
    public static CreateChallengeRequest buildCreateChallengeRequest(Challenge challenge) {
        CreateChallengeRequest request = new CreateChallengeRequest();

        request.setChallengerId(challenge.getChallengerId());
        request.setOpponentId(challenge.getOpponentId());
        request.setWord(challenge.getWord());
        MapPoint mp = challenge.getMapPoint();
        request.setLat(mp.getLat());
        request.setLng(mp.getLng());
        request.setZoom(mp.getZoom());

        return request;
    }

    /**
     * This method creates a Create Challenge Image request to be sent to the server
     *
     * @param challenge The challenge where image to be sent will be extracted
     *
     * @return Create Challenge Image request
     */
    public static CreateChallengeImageRequest buildCreateImageChallengeRequest(
            Challenge challenge) {
        CreateChallengeImageRequest request = new CreateChallengeImageRequest();
        request.setChallengeId(challenge.getId());
        String encodedImage = Base64
                .encodeToString(ImageUtils.buildBitmapByteArray(challenge.getPic()),
                        Base64.DEFAULT);
        request.setImageBytes(encodedImage);

        return request;
    }

    /**
     * This method builds a Properties instance given the context and the properties file name
     *
     * @param context        Current Context
     * @param propertiesFile Properties file name
     *
     * @return Properties instance with properties loaded
     *
     * @throws IOException
     */
    private Properties buildProperties(Context context, String propertiesFile) throws IOException {
        InputStreamReader isr = new InputStreamReader(context.getAssets().open(propertiesFile),
                PROPERTIES_FILE_CHARSET);
        Properties properties = new Properties();
        properties.load(isr);
        return properties;
    }

    /**
     * This method returns an specific URL given a URL Type
     *
     * @param type requested URL type
     *
     * @return requested URL
     *
     * @throws IllegalArgumentException If requested type is not supported
     */
    public String getUrl(ServerUrlTypes type) throws IllegalArgumentException {

        StringBuilder url = new StringBuilder(
                properties.getProperty(ServerUrlTypes.DEFAULT.getProperty()));

        switch (type) {
            case DEFAULT:
                // Server Default URL already in url
                break;
            case USERS:
            case CHALLENGES:
            case REQUEST_CHALLENGES:
            case CHALLENGES_IMAGE:
            case REQUEST_CHALLENGES_IMAGE:
                url.append(properties.getProperty(type.getProperty()));
                break;
            case FRIENDS:
            case REGISTERED_FRIENDS:
            case TOKENS:
                url.append(properties.getProperty(ServerUrlTypes.USERS.getProperty()));
                url.append(properties.getProperty(type.getProperty()));
                break;
            case GOOGLE_PROFILE_PICTURE:
                url = new StringBuilder(properties.getProperty(type.getProperty()));
                url.append(properties.getProperty(BROWSER_KEY));
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Server Url Type not supported [%s]", type.name()));
        }

        return url.toString();
    }

    /**
     * This method builds a Create/Update User Request
     *
     * @param userInfo the User to be wrapped in the request
     *
     * @return Request
     *
     * @throws NoSuchAlgorithmException
     */
    public CreateUpdateUserRequest buildUserRequest(UserInfo userInfo) throws
            NoSuchAlgorithmException {
        CreateUpdateUserRequest request = new CreateUpdateUserRequest();

        request.setTest(Boolean.FALSE);
        request.setSecurityKey(buildSecurityKey());
        request.setUser(userInfo);

        return request;
    }

    /**
     * This method builds a Create/Update User Request
     *
     * @param friends the User's friends
     *
     * @return Request
     *
     * @throws NoSuchAlgorithmException
     */
    public CreateUpdateFriendsRequest buildUserFriendsRequest(List<UserInfo> friends) throws
            NoSuchAlgorithmException {
        CreateUpdateFriendsRequest request = new CreateUpdateFriendsRequest();

        request.setTest(Boolean.FALSE);
        request.setSecurityKey(buildSecurityKey());
        request.setFriends(friends);

        return request;
    }

    /**
     * Builds Security key
     *
     * @return Build security key
     *
     * @throws NoSuchAlgorithmException
     */
    private String buildSecurityKey() throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        sb.append(properties.get(SECURE_KEY)).append(SEPARATOR).append(properties.get(PARAM1))
                .append(SEPARATOR).append(properties.get(PARAM2));

        MessageDigest cript = MessageDigest.getInstance(ALGORITHM);
        cript.reset();
        cript.update(sb.toString().getBytes());

        String codec = new String(Hex.encodeHex(cript.digest()));
        return codec;
    }

}
