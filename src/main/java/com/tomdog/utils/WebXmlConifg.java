package com.tomdog.utils;

import com.tomdog.version3.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebXmlConifg {
    private static Map<String, HttpServlet> servletMap = new HashMap<>();

    public WebXmlConifg() throws DocumentException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        load();
    }


    public void load() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(resourceAsStream);
        Element rootElement = document.getRootElement();
        List<Element> selectNodes = rootElement.selectNodes("//servlet");
        for (int i = 0; i < selectNodes.size(); i++) {
            Element element =  selectNodes.get(i);
            Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
            String servletName = servletnameElement.getStringValue();

            // <servlet-class>server.LagouServlet</servlet-class>

            Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
            String servletClass = servletclassElement.getStringValue();

            Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");

            String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

            HttpServlet httpServlet = (HttpServlet) Class.forName(servletClass).newInstance();

            servletMap.put(urlPattern, httpServlet);

        }
    }

    public Map<String, HttpServlet> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, HttpServlet> servletMap) {
        this.servletMap = servletMap;
    }
}
