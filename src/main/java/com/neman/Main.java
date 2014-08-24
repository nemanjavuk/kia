package com.neman;

import com.neman.data.HighScoresHashStorage;
import com.neman.server.filter.ParamsFilter;
import com.neman.server.handler.RequestRouterImpl;
import com.neman.session.SessionManagerImpl;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by nemanja on 8/21/14.
 */
public class Main {

    private static final int PORT = 9000;


    public static void main(String[] args) throws IOException {
        // Create an http main.java.com.neman.server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Create a context
        HttpContext context = server.createContext("/", new RequestRouterImpl(new SessionManagerImpl(), new HighScoresHashStorage()));

        // Add a filter
        context.getFilters().add(new ParamsFilter());

        // Set an Executor for the multi-threading with fixed amount of 10 threads
        server.setExecutor(Executors.newFixedThreadPool(10));

        // Start the main.java.com.neman.server
        server.start();

        System.out.println("The com.neman.server is started!");


    }
}
