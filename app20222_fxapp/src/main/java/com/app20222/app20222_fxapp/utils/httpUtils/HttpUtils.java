package com.app20222.app20222_fxapp.utils.httpUtils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpMethods;
import com.google.common.net.HttpHeaders;
import org.json.JSONObject;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

public class HttpUtils {

    public static <T> T doRequest(String uri, String method, TypeReference<T> type, Object reqBody) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonBody = new JSONObject(reqBody);
        boolean isContainResBody = !Objects.isNull(type);
        try {
            switch (method) {
                case HttpMethods.GET:
                    String[] getHeaders = {HttpHeaders.ACCEPT, "application/json"};
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .GET()
                            .headers(getHeaders)
                            .timeout(Duration.ofMillis(10000)) // 10s time-out
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return mapper.readValue(response.body(), type);
                case HttpMethods.POST:
                    String[] postHeaders = {HttpHeaders.CONTENT_TYPE, "application/json"};
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                            .headers(postHeaders)
                            .timeout(Duration.ofMillis(10000)) // 10s time-out
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return isContainResBody ? mapper.readValue(response.body(), type) : null;
                case HttpMethods.PUT:
                    String[] putHeaders = {HttpHeaders.CONTENT_TYPE, "application/json"};
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .PUT(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                            .headers(putHeaders)
                            .timeout(Duration.ofMillis(10000)) // 10s time-out
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return isContainResBody ? mapper.readValue(response.body(), type) : null;
                default:
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
