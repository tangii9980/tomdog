package com.tomdog.version3;

import com.tomdog.version2.Request;
import com.tomdog.version2.Response;

import java.io.IOException;

public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request, Response response) throws IOException;

    public abstract void doPost(Request request, Response response) throws IOException;

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }

    @Override
    public void service(Request request, Response response) throws Exception {
        if(request.getMethod().equalsIgnoreCase("GET")){
            doGet(request,response);
        }else {
            doPost(request,response);
        }
    }

}
