package com.tomdog.version2;

import com.tomdog.utils.HttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     *
     * @param content
     * @throws IOException
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    public void outputHtml(String path) throws IOException {
        // get absolute path of static resource
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);

        // output static resource file
        File file = new File(absoluteResourcePath);
        if(file.exists() && file.isFile()) {
            StaticResourceUtil.outputStaticResource(path,new FileInputStream(file),outputStream);
        }else{
            // output 404
            output(HttpUtil.getHttpHeader404());
        }

    }

}
