package com.tomdog.version1;

import com.sun.istack.internal.NotNull;
import com.tomdog.utils.HttpUtil;
import com.tomdog.utils.WebXmlConifg;
import com.tomdog.version2.Request;
import com.tomdog.version2.Response;
import com.tomdog.version3.HttpServlet;
import com.tomdog.version4.RequestProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

public class MyFirstTomdogApp {
    private static Logger logger = LogManager.getLogger(MyFirstTomdogApp.class.getName());
    private int port;
    private Map<String, HttpServlet> servletMap;
    private ThreadPoolExecutor threadPoolExecutor;

    public MyFirstTomdogApp(int port){
        this.port = port;
    }


    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("tomdog start on port:"+port);

        init();

        crateTreadPool();

        while(true){
            //receive the request
            Socket socket = serverSocket.accept();

            //write some service code...
            logger.info("tomdog serving");
//            serve1(socket);
//            serve2(socket);
//            serve3(socket);
            process(socket);
            //close connection
//            close(socket);
        }

    }

    private void init() throws ClassNotFoundException, InstantiationException, DocumentException, IllegalAccessException {

        WebXmlConifg webXmlConifg = new WebXmlConifg();

        servletMap = webXmlConifg.getServletMap();
    }

    /**
     *
     * @param socket
     * @throws IOException
     */
    private void serve1(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        String data = "hello,this is a tom dog!";
        String responeText = HttpUtil.getHttpHeader200(data.length()) + data;
        outputStream.write(responeText.getBytes());
    }

    /**
     *
     * @param socket
     * @throws IOException
     */
    private void close(Socket socket) throws IOException {
        socket.close();
    }

    private void serve2(Socket accept) throws IOException {

        InputStream inputStream = accept.getInputStream();

        Request request = new Request(inputStream);

        Response response = new Response(accept.getOutputStream());

        response.outputHtml(request.getUrl());

    }

    private void serve3(Socket accept) throws Exception {

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

    private void process(Socket accept) {
        RequestProcessor requestProcessor = new RequestProcessor(accept,servletMap);
//        requestProcessor.start();
        threadPoolExecutor.execute(requestProcessor);
    }

    private void crateTreadPool() {
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

    public static void main(String[] args) throws Exception {
        MyFirstTomdogApp myFirstTomdogApp = new MyFirstTomdogApp(8081);
        myFirstTomdogApp.start();
    }
}
