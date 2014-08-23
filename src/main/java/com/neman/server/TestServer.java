package main.java.com.neman.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nemanja on 8/22/14.
 */
public class TestServer implements Runnable {

    private final static int PORT = Integer.getInteger("test.port", 8800);
    private static TestServer serverInstance;
    private HttpServer httpServer;
    private ExecutorService executor;

    @Override
    public void run() {
        try {
            executor = Executors.newFixedThreadPool(20);

            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/test", new TestHandler());
            httpServer.setExecutor(executor);
            httpServer.start();
            System.out.println("Started TestServer at port " + PORT);

            // Wait here until notified of shutdown.
            synchronized (this) {
                try {
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    static void shutdown() {

        try {
            System.out.println("Shutting down TestServer.");
            serverInstance.httpServer.stop(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (serverInstance) {
            serverInstance.notifyAll();
        }

    }

    public static void main(String[] args) throws Exception {

        serverInstance = new TestServer();

        Thread serverThread = new Thread(serverInstance);
        serverThread.start();

        Runtime.getRuntime().addShutdownHook(new OnShutdown());

        try {
            serverThread.join();
        } catch (Exception e) {
        }
    }

}

/* Responds to the /test URI. */
class TestHandler implements HttpHandler {

    boolean debug = Boolean.getBoolean("test.debug");

    public void handle(HttpExchange exchange) throws IOException {

        System.out.println(this);  // ALWAYS SAME THREAD!

        String response = "RESPONSE AT " + System.currentTimeMillis();

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
}

/* Responds to a JVM shutdown by stopping the main.java.com.neman.server. */
class OnShutdown extends Thread {
    public void run() {
        TestServer.shutdown();
    }
}
