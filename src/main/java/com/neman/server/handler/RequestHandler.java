package com.neman.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private HttpHandlerFactory factory;

    public RequestHandler() {
        factory = new HttpHandlerFactory();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = factory.createHandler(httpExchange);
        handler.handle(httpExchange);
    }
}
