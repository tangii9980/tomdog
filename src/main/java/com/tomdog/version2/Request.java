package com.tomdog.version2;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private String method; // request method，such as GET/POST
    private String url;  // content like: /index.html
    private InputStream inputStream;  //  Input Stream,parse attributes from this

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        int count = 0;
        while(count == 0){
            count = inputStream.available();
        }

        byte[] bytes = new byte[count];
        inputStream.read(bytes);

        String inputStr = new String(bytes);
        // get first line in request header
        String firstLineStr = inputStr.split("\\n")[0];  // GET / HTTP/1.1

        String[] strings = firstLineStr.split(" ");

        this.method = strings[0];
        this.url = strings[1];

//        System.out.println("=====>>method:" + method);
//        System.out.println("=====>>url:" + url);
    }

    public String getUrl() {
        return url;
    }
}
