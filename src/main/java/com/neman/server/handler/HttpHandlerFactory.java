package com.neman.server.handler;

import com.neman.data.HighScores;
import com.neman.session.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHandlerFactory {
    public HttpHandler createHandler(HttpExchange httpExchange, SessionManager sessionManager, HighScores highScores) {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        String path = uri.getPath();

        Pattern pattern = Pattern.compile("/(\\d+)/(?<pathel>.*)");
        Matcher matcher = pattern.matcher(path);

        String pathEl = "";

        if (matcher.find()) {
            pathEl = matcher.group("pathel");
        }

        if ("get".equalsIgnoreCase(method)) {
            if ("login".equalsIgnoreCase(pathEl)) {
                return new LoginHandler(sessionManager);
            }
            if ("highscorelist".equalsIgnoreCase(pathEl)) {
                return new HighScoresHandler(highScores);
            }
        }

        if ("post".equalsIgnoreCase(method)) {
            if ("score".equalsIgnoreCase(pathEl)) {
                return new InsertScoreHandler(sessionManager, highScores);
            }
        }

        return new ResourceNotFoundHandler();
    }
}
