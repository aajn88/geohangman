package com.doers.games.geohangman.utils;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *
 * Restful Utils class. This class implements requesting methods as GET and POST
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class RestUtils {

    /** Private default constructor **/
    private RestUtils() {}

    /**
     * Given a URL, a Class Type and a map with variables, this method performs GET method
     * request to the given url
     *
     * @param url The URL where GET request will be done.
     * @param responseClass Expected response class
     * @param urlVariables Url Variables
     * @param <T> Expected return class
     * @return Get method response
     */
    public static <T> T get(String url, Class<T> responseClass, Map<String, ?> urlVariables) {
        RestTemplate template = getRestTemplate();
        return template.getForObject(url, responseClass, urlVariables);
    }

    /**
     *
     * Given a URL, a request and a Class Type, this method performs POST
     * method
     * request to the given url
     *
     * @param url The URL where POST request will be done.
     * @param request Request to be sent. Could be null
     * @param responseClass Expected response class
     * @param <T> Expected response class
     * @return POST request response
     */
    public static <T> T post(String url, Object request, Class<T> responseClass) {
        return post(url, request, responseClass, null);
    }

    /**
     *
     * Given a URL, a request, a Class Type and a map with variables, this method performs POST
     * method
     * request to the given url
     *
     * @param url The URL where POST request will be done.
     * @param request Request to be sent. Could be null
     * @param responseClass Expected response class
     * @param urlVariables url Variables. Could be null
     * @param <T> Expected response class
     * @return POST request response
     */
    public static <T> T post(String url, Object request, Class<T> responseClass, Map<String, ?>
            urlVariables) {
        RestTemplate template = getRestTemplate();
        T response;

        if(urlVariables != null) {
            response = template.postForObject(url, request, responseClass, urlVariables);
        } else {
            response = template.postForObject(url, request, responseClass);
        }

        return response;
    }

    /**
     * Get a configured RestTemplate
     * @return configured restTemplate
     */
    private static RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new GsonHttpMessageConverter());
        return template;
    }
}
