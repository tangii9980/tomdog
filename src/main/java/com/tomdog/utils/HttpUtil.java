package com.tomdog.utils;

/**
 * this is a HTTP protocol util, which mainly provide HTTP Response header information
 * we only handle status code 200 , 404 and static resource here
 */
public class HttpUtil {

    /**
     * provide response header for status code 200
     */
    public static String getHttpHeader200(long contentLength){
        String header = "HTTP/1.1 200 OK\n" +
                "content-type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
        return  header;
    }

    /**
     * provide response header for status code 404
     */
    public static String getHttpHeader404(){
        String str404 = "<h1>404 not found</h1>";
        String header = "HTTP/1.1 200 OK\n" +
                "content-type: text/html \n" +
                "Content-Length: " + str404.length() + " \n" +
                "\r\n" + str404;
        return  header;
    }

    public static String getHttpHeaderImg(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: image/png \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }
}
