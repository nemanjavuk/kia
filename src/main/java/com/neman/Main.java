package com.neman;

import com.neman.domain.HighScoresHashStorage;
import com.neman.server.filter.ParamsFilter;
import com.neman.server.handler.RequestRouterImpl;
import com.neman.session.SessionManagerImpl;
import com.neman.utils.RandomKeyGenerator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by nemanja on 8/21/14.
 */
public class Main {

    private static final int PORT = 8081;


    public static void main(String[] args) throws IOException {
        // Create an http main.java.com.neman.server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        //Create a session key generator
        //with current implementation this can be a time consuming process
        RandomKeyGenerator keyGenerator = new RandomKeyGenerator();
        // Create a context
        HttpContext context = server.createContext("/", new RequestRouterImpl(new SessionManagerImpl(keyGenerator), new HighScoresHashStorage()));

        // Add a filter
        context.getFilters().add(new ParamsFilter());

        // Set an Executor for the multi-threading with fixed amount of 10 threads
        server.setExecutor(Executors.newFixedThreadPool(10));

        // Start the server
        server.start();

        System.out.println("The server is started on port " + PORT);


    }
}
