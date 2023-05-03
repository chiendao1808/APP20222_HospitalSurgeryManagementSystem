package com.app20222.app20222_fxapp.utils.httpUtils;


import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpMethods;
import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONObject;


import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpUtils {

    /**
     * Execute a HTTP Request with JSON Payload in both request and response
     */
    public static <T> T doRequest(String uri, String method, TypeReference<T> type, Object reqBody, Map<String, String> headers) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonBody = new JSONObject(reqBody);
        String[] reqHeaders = buildHeaders(headers);
        boolean isContainResBody = !Objects.isNull(type);
        try {
            switch (method) {
                case HttpMethods.GET:
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .GET()
                            .header(HttpHeaders.ACCEPT, "application/json")
                            .headers(reqHeaders)
                            .timeout(Duration.ofMillis(10000)) // 10s time-out
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return mapper.readValue(response.body(), type);
                case HttpMethods.POST:
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                            .header(HttpHeaders.CONTENT_TYPE, "application/json")
                            .headers(reqHeaders)
                            .timeout(Duration.ofMillis(10000)) // 10s time-out
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return isContainResBody ? mapper.readValue(response.body(), type) : null;
                case HttpMethods.PUT:
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .PUT(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                            .header(HttpHeaders.CONTENT_TYPE, "application/json")
                            .headers(reqHeaders)
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

    /**
     * Execute a request upload file
     */
    public static BaseResponse doUploadFile(String uri, File file, Map<String, String> headers){
        ObjectMapper mapper = new ObjectMapper();
        String[] reqHeaders = buildHeaders(headers);
        try {
            // Build Multipart entity
            HttpClient client = HttpClient.newHttpClient();
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("uploaded_file", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .build();
            // Write file using a pipeline stream to avoided overflow
            Pipe pipe = Pipe.open(); // Open a pipeline stream
            // Use new thread to avoid deadlock when write data
            new Thread(() -> {
                try (OutputStream outputStream = Channels.newOutputStream(pipe.sink())) {
                    // Write Data to pipeline
                    httpEntity.writeTo(outputStream);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
            // Create a http request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header(HttpHeaders.CONTENT_TYPE, httpEntity.getContentType().getValue())
                    .headers(reqHeaders)
                    .POST(HttpRequest.BodyPublishers.ofInputStream(() -> Channels.newInputStream(pipe.source())))
                    .timeout(Duration.ofMillis(10000)) // 10s  time-out
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), BaseResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new BaseResponse(204, "Upload file fail!", LocalDateTime.now());
        }
    }

    /**
     * Build headers for java 11 http request
     */
    public static String[] buildHeaders(Map<String, String> headers) {
        List<String> lstKeyAndValue = new ArrayList<>();
        headers.forEach((key, value) -> {
            lstKeyAndValue.add(key);
            lstKeyAndValue.add(value);
        });
        return lstKeyAndValue.toArray(String[]::new);
    }

}
