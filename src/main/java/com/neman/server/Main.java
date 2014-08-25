package com.neman.server;

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
 * Created by nemanja.
 */
public class Main {

    private static final int PORT = 8081;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        //Create a session key generator
        //with current implementation this can be a time consuming process
        RandomKeyGenerator keyGenerator = new RandomKeyGenerator();
        HttpContext context = server.createContext("/", new RequestRouterImpl(new SessionManagerImpl(keyGenerator), new HighScoresHashStorage()));
        context.getFilters().add(new ParamsFilter());
        server.setExecutor(Executors.newFixedThreadPool(10));

        server.start();

        System.out.println("The server is started on port " + PORT);


    }
}
