package com.tomdog.version2;

import com.tomdog.utils.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StaticResourceUtil {
    /**
     *
     * @param path
     * @return
     * access abs path of static resource
     */
    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\","/") + path;
    }


    /**
     *
     * @param path
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void outputStaticResource(String path, InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }

        int resourceSize = count;

        if (path.endsWith(".png") || path.endsWith(".jpg")) {
            // output HTTP request header,then output content
            outputStream.write(HttpUtil.getHttpHeaderImg(resourceSize).getBytes());
        } else {
            outputStream.write(HttpUtil.getHttpHeader200(resourceSize).getBytes());
        }

        long written = 0;// length of content have read
        int byteSize = 1024; // Preset length of buffer each time
        byte[] bytes = new byte[byteSize];

        while (written < resourceSize) {
            if (written + byteSize > resourceSize) {  // this indicate that remainder under 1024 bytes
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);

            outputStream.flush();
            written += byteSize;
        }
    }
}
