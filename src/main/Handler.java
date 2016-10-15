package main;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import static main.Server.requestMap;

/**
 * Created by leelddd on 9/23/2016.
 */
public class Handler implements Runnable {

    Socket socket;
    Request request;
    Response response;

    public Handler (Socket socket) throws IOException {
        this.socket = socket;
    }

    public void handle (String path) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (isStatic(path)) {
            sendStaticFile();
        } else {
            dispachUrl(path);
        }
    }

    private boolean isStatic (String path) {
        return path.contains(".");
    }

    private void sendStaticFile () throws IOException {
        this.response.sendStaticResource();
    }

    private void dispachUrl (String path) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class object = requestMap.get(path);
        Method method = requestMap.get(path).getDeclaredMethod("service", ServletRequest.class, ServletResponse.class);
        method.invoke(object.newInstance(), request, response);
    }

    @Override
    public void run () {
        try {
            request = new Request(socket.getInputStream());
            request.parse();
            response = new Response(socket.getOutputStream());
            response.setRequest(request);
            handle(request.getPath());
            response.close();
            socket.close();
        } catch (IOException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("Take it easy");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("Servlet method not found");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Nothing to worry");
        }
    }
}