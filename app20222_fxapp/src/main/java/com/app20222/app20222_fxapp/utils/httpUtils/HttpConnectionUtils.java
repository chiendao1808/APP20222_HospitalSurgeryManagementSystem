package com.app20222.app20222_fxapp.utils.httpUtils;

import java.net.URL;
import java.net.URLConnection;

public class HttpConnectionUtils {

    public static URLConnection connect(String url){
        try{
            return new URL(url).openConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
