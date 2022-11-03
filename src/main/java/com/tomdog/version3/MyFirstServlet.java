package com.tomdog.version3;

import com.tomdog.utils.HttpUtil;
import com.tomdog.version1.MyFirstTomdogApp;
import com.tomdog.version2.Request;
import com.tomdog.version2.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MyFirstServlet extends HttpServlet{

    private static Logger logger = LogManager.getLogger(MyFirstTomdogApp.class.getName());

    @Override
    public void doGet(Request request, Response response) throws IOException {
        String content = "<h1>this is a servlet GET method</h1>";
        response.output(HttpUtil.getHttpHeader200(content.getBytes().length)+content);
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {
        logger.info("this is doPost and it call doGet");
        doGet(request,response);
    }
}
