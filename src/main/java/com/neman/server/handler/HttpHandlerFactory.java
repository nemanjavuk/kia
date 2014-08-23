package com.neman.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHandlerFactory {
    public HttpHandler createHandler(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        String path = uri.getPath();

//        Pattern pattern = Pattern.compile("/(\\d+)/(?<pathel>.*)");
        Pattern pattern = Pattern.compile("/(\\d+)/(?<verb>.*)");
        Matcher matcher = pattern.matcher(path);

        String pathEl = "";

        if (matcher.find()) {
            pathEl = matcher.group("verb");
        }

        if ("login".equalsIgnoreCase(pathEl) && "get".equalsIgnoreCase(method)) {
            return new LoginHandler();
        } else if ("score".equalsIgnoreCase(pathEl) && "post".equalsIgnoreCase(method)) {
            return new InsertScoreHandler();
        } else if ("highscorelist".equalsIgnoreCase(pathEl) && "get".equalsIgnoreCase(method)) {
            return new HighScoresHandler();
        }

        return new NotFoundHandler();
    }
}
