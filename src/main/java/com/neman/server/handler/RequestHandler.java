package com.neman.server.handler;

import com.neman.data.HighScores;
import com.neman.session.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private final SessionManager sessionManager;
    private final HighScores highScores;

    private HttpHandlerFactory factory;

    public RequestHandler(SessionManager sessionManager, HighScores highScores) {
        factory = new HttpHandlerFactory();
        this.sessionManager = sessionManager;
        this.highScores = highScores;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = factory.createHandler(httpExchange, sessionManager, highScores);
        handler.handle(httpExchange);
    }
}
