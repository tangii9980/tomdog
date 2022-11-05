package com.tomdog.version4;

import com.tomdog.version2.Request;
import com.tomdog.version2.Response;
import com.tomdog.version3.HttpServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread{
    private Socket accept;
    private Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket accept, Map<String, HttpServlet> servletMap) {
        this.accept = accept;
        this.servletMap = servletMap;
    }

    public void run() {
        try {
            process();
            Thread.sleep(5000);
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void process() throws Exception {

        InputStream inputStream = accept.getInputStream();

        Request request = new Request(inputStream);

        Response response = new Response(accept.getOutputStream());

        if(servletMap.get(request.getUrl()) == null) {
            response.outputHtml(request.getUrl());
        }else{
            HttpServlet httpServlet = servletMap.get(request.getUrl());
            httpServlet.service(request,response);
        }
    }

    private void close() throws IOException {
        accept.close();
    }
}
