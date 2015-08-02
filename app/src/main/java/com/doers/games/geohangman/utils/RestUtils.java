package com.doers.games.geohangman.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
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
     * @param <T> Expected return class
     * @return Get method response
     */
    public static <T> T get(String url, Class<T> responseClass) {
        return get(url, responseClass, null);
    }

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
        return (urlVariables != null ? template.getForObject(url, responseClass, urlVariables) : template.getForObject(url, responseClass));
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
     * This method posts multipart files
     *
     * @param url URL Target
     * @param vars URL vars
     * @param files Files to be uploaded
     * @param responseClass Expected response class
     * @param <T> Response Class
     * @return Response entity with an instance of the expected response class
     */
    public static <T> ResponseEntity<T> postFiles(String url, Map<String, String> vars,
                                                  Map<String, File> files, Class<T> responseClass) {
        RestTemplate template = getRestTemplate(Boolean.TRUE);
        MultiValueMap<String, Object> values = new LinkedMultiValueMap<>();

        if (vars != null) {
            for (Map.Entry<String, String> var : vars.entrySet()) {
                values.add(var.getKey(), var.getValue());
            }
        }

        if(files != null) {
            for (Map.Entry<String, File> fileMap : files.entrySet()) {
                File file = fileMap.getValue();
                values.add(fileMap.getKey(), new FileSystemResource(file.getPath()));
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity request = new HttpEntity(values, headers);

        ResponseEntity<T> response = template.exchange(url, HttpMethod.POST, request, responseClass);

        return response;
    }

    /**
     * Get a configured RestTemplate
     * @return configured restTemplate
     */
    private static RestTemplate getRestTemplate() {
        return getRestTemplate(Boolean.FALSE);
    }

    private static RestTemplate getRestTemplate(Boolean multipart) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new GsonHttpMessageConverter());
        if(multipart) {
            template.getMessageConverters().add(new FormHttpMessageConverter());
        }
        return template;
    }
}
