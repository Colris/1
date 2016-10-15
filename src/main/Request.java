package main;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Request implements ServletRequest {

    private InputStream input;
    private String url;
    private String path;
    private Map<String, String> params = new HashMap<>();

    public Request (InputStream input) {
        this.input = input;
    }

    public String getUrl () {
        return url;
    }

    private void parseUrl (String requestString) {
        int index1 = requestString.indexOf(' ');
        int index2 = requestString.indexOf(' ', index1 + 1);
        this.url = requestString.substring(index1 + 1, index2);
        if (url.contains("?")) {
            this.path = url.substring(0, url.indexOf('?'));
            String p = url.substring(url.indexOf('?') + 1, url.length());
            String[] pa = p.split("&");
            for (String pair : pa) {
                String[] par = pair.split("=");
                params.put(par[0], par[1]);
            }
        } else {
            this.path = url;
        }
    }

    public String getPath () {
        return this.path;
    }

    public void parse () throws IOException {
        byte[] buffer = new byte[2048];
        int length = input.read(buffer);
        if (length > 0)
            parseUrl(new String(buffer));
    }

    /* implementation of the ServletRequest */
    public Object getAttribute (String attribute) {
        return null;
    }

    public Enumeration<String> getAttributeNames () {
        return null;
    }

    public String getRealPath (String path) {
        return null;
    }

    public RequestDispatcher getRequestDispatcher (String path) {
        return null;
    }

    public boolean isSecure () {
        return false;
    }

    public String getCharacterEncoding () {
        return null;
    }

    public int getContentLength () {
        return 0;
    }

    public String getContentType () {
        return null;
    }

    public ServletInputStream getInputStream () throws IOException {
        return null;
    }

    public Locale getLocale () {
        return null;
    }

    public Enumeration<Locale> getLocales () {
        return null;
    }

    public String getParameter (String name) {
        return this.params.get(name);
    }

    public Map<String, String[]> getParameterMap () {
        return null;
    }

    public Enumeration<String> getParameterNames () {
        return null;
    }

    public String[] getParameterValues (String parameter) {
        return null;
    }

    public String getProtocol () {
        return null;
    }

    public BufferedReader getReader () throws IOException {
        return null;
    }

    public String getRemoteAddr () {
        return null;
    }

    public String getRemoteHost () {
        return null;
    }

    public String getScheme () {
        return null;
    }

    public String getServerName () {
        return null;
    }

    public int getServerPort () {
        return 0;
    }

    public void removeAttribute (String attribute) {
    }

    public void setAttribute (String key, Object value) {
    }

    public void setCharacterEncoding (String encoding)
            throws UnsupportedEncodingException {
    }

    @Override
    public AsyncContext getAsyncContext () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DispatcherType getDispatcherType () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLocalAddr () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLocalName () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLocalPort () {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getRemotePort () {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ServletContext getServletContext () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAsyncStarted () {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAsyncSupported () {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AsyncContext startAsync () throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AsyncContext startAsync (ServletRequest servletRequest,
                                    ServletResponse servletResponse) throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

}