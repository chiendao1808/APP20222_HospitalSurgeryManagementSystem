package com.app20222.app20222_fxapp.utils.httpUtils;


import com.app20222.app20222_fxapp.constants.apis.ApiConstants;
import com.app20222.app20222_fxapp.context.ApplicationContext;
import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;


import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class HttpUtils {

    public static ObjectMapper mapper = new ObjectMapper();


    /**
     * Execute an HTTP Request with JSON Payload in both request and response
     */
    public static HttpResponse<String> doRequest(String uri, String method, Object reqBody, Map<String, String> headers) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        CompletableFuture<HttpResponse<String>> response;
        ObjectMapper mapper = new ObjectMapper();
        List<String> reqHeaders = buildHeaders(headers, uri);
        reqHeaders.addAll(List.of(HttpHeaders.CONTENT_TYPE, "application/json"));
        try {
            switch (method) {
                case HttpMethods.GET:
                    request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .GET()
                        .headers(reqHeaders.toArray(String[]::new))
                        .timeout(Duration.ofMillis(10000)) // 10s time-out
                        .build();
                    response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    return response.join();
                case HttpMethods.POST:
                    request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(reqBody)))
                        .headers(reqHeaders.toArray(String[]::new))
                        .timeout(Duration.ofMillis(10000)) // 10s time-out
                        .build();
                    response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    return response.join();
                case HttpMethods.PUT:
                    request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(reqBody)))
                        .headers(reqHeaders.toArray(String[]::new))
                        .timeout(Duration.ofMillis(10000)) // 10s time-out
                        .build();
                    response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    return response.join();
                default:
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * Mapping response body if required
     */
    public static <T> T mappingResponseBody(HttpResponse<String> response, TypeReference<T> type) {
        try {
            return Objects.nonNull(response) ? mapper.readValue(response.body(), type) : null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * Execute a request upload file
     */
    public static BaseResponse doUploadFile(String uri, File file, Map<String, String> headers) {
        List<String> reqHeaders = buildHeaders(headers, uri);
        reqHeaders.addAll(List.of(HttpHeaders.CONTENT_TYPE, "multipart/form-data"));
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
                .headers(reqHeaders.toArray(String[]::new))
                .POST(HttpRequest.BodyPublishers.ofInputStream(() -> Channels.newInputStream(pipe.source())))
                .timeout(Duration.ofMillis(10000)) // 10s  time-out
                .build();
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            return mapper.readValue(response.body(), BaseResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new BaseResponse(204, "Upload file fail!", new Date());
        }
    }

    /**
     * Build headers for java 11 http request
     */
    public static List<String> buildHeaders(Map<String, String> headers, String uri) {
        List<String> lstKeyAndValue = new ArrayList<>();
        headers.forEach((key, value) -> {
            lstKeyAndValue.add(key);
            lstKeyAndValue.add(value);
        });
        // Add Authorization header if is auth api
        if (!(uri.contains("/auth") || uri.contains("/public/"))) {
            lstKeyAndValue.add(HttpHeaders.AUTHORIZATION);
            lstKeyAndValue.add(ApiConstants.AUTH_SCHEME + " " + ApplicationContext.accessToken);
        }
        return lstKeyAndValue;
    }

    /**
     * Handle response
     */
    public static <T> Object handleResponse(HttpResponse<String> response, T resDTO, Class<T> clazz, ExceptionResponse exResponse){
        boolean success = false;
        try{
            if(Objects.isNull(response)) return null;
            switch (response.statusCode()){
                case HttpStatusCodes.STATUS_CODE_OK:
                case HttpStatusCodes.STATUS_CODE_ACCEPTED:
                    resDTO = mapper.readValue(response.body(), clazz);
                    success = true;
                    break;
                default:
                    exResponse = mapper.readValue(response.body(), ExceptionResponse.class);
                    break;
            }
            return success ? resDTO : exResponse;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

}
