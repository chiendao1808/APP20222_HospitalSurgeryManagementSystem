package com.app20222.app20222_fxapp.utils.apiUtils;

import com.app20222.app20222_fxapp.constants.apis.ApiConstants;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;

public class ApiUtils {

    /**
     * Build HTTP URL Request
     * @param detailPath : đường dẫn chi tiết của API
     * @param params : các tham số truyền vào URL
     */
    public static String buildURI(String detailPath, Map<String, String> params) {
        try {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme(ApiConstants.HTTP_SCHEME);
            uriBuilder.setHost(ApiConstants.API_LOCAL_IP);
            uriBuilder.setPort(ApiConstants.SERVER_PORT);
            uriBuilder.setPath(ApiConstants.DEFAULT_API_PATH + detailPath);
            params.forEach((key, value) -> uriBuilder.addParameter(key, value));
            return uriBuilder.build().toString();
        } catch (URISyntaxException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
