package com.tomdog.version3;

import com.tomdog.version2.Request;
import com.tomdog.version2.Response;

public interface Servlet {
    void init() throws Exception;
    void service(Request request, Response response) throws Exception;
    void destory() throws Exception;
}
