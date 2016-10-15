package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private static final int PORT = 9988;
    private final static String WEBROOT = "WebRoot" + File.separator + "WEB-INF" + File.separator + "web.xml";
    public static Map<String, Class> requestMap = new HashMap<>();

    static void start () throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
        config();
        ServerSocket serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
        while (true) {
            new Thread(new Handler(serverSocket.accept())).start();
        }
    }

    private static void config () throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new File(WEBROOT));
        Element root = document.getDocumentElement();
        NodeList classNodes = root.getElementsByTagName("servlet");
        NodeList urlNodes = root.getElementsByTagName("servlet-mapping");
        Map<String, String> classMap = new HashMap<>();
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element element = (Element) classNodes.item(i);
            classMap.put(
                    element.getElementsByTagName("servlet-name").item(0).getFirstChild().getNodeValue(),
                    element.getElementsByTagName("servlet-class").item(0).getFirstChild().getNodeValue()
            );
        }
        Map<String, String> urlMap = new HashMap<>();
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element element = (Element) urlNodes.item(i);
            urlMap.put(
                    element.getElementsByTagName("servlet-name").item(0).getFirstChild().getNodeValue(),
                    element.getElementsByTagName("url-pattern").item(0).getFirstChild().getNodeValue()
            );
        }
        for (Map.Entry<String, String> entry : classMap.entrySet()) {
            requestMap.put(
                    urlMap.get(entry.getKey()),
                    Class.forName(entry.getValue())
            );
        }
    }


    public static void main (String[] args) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
        new Server().start();
    }
}