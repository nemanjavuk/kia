package com.neman.server.handler;

import com.neman.data.HighScores;
import com.neman.data.Score;
import com.neman.session.SessionManager;
import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertScoreHandler implements HttpHandler {

    private final HighScores highScores;
    private final SessionManager sessionManager;

    public InsertScoreHandler(SessionManager sessionManager, HighScores highScores) {
        this.sessionManager = sessionManager;
        this.highScores = highScores;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String sessionKey = getSessionKey(httpExchange);
        int level = getLevel(httpExchange);
        int score = getScore(httpExchange);
        int userId = sessionManager.getUserId(sessionKey);

        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        try {
            responseHeaders.set("Content-Type", "text/plain");
            if (userId != -1 && level != -1 && score != -1) {
                highScores.putScore(level, new Score(userId, score));
                httpExchange.sendResponseHeaders(200, 0);
                responseBody.close();
            } else {
                //user doesn't exist
                httpExchange.sendResponseHeaders(400, 0);
                responseBody.close();

            }
        } catch (IOException ioe) {
            IOUtils.closeQuietly(responseBody);
        }
    }

    private int getLevel(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();

        Pattern pattern = Pattern.compile("/(?<level>\\d+)/score");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group("level"));
            } catch (NumberFormatException nfe) {
                return -1;
            }
        }

        return -1; //TODO:nemanja:think about returning zeros
    }

    private String getSessionKey(HttpExchange httpExchange) {
        return getParam(httpExchange, "sessionkey");
    }

    private int getScore(HttpExchange httpExchange) {
        String scoreString = getParam(httpExchange, "score");
        try {
            return Integer.parseInt(scoreString);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }


    private String getParam(HttpExchange httpExchange, String paramKey) {
        Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
        if (params.containsKey(paramKey)) {
            return (String) params.get(paramKey);
        }
        return "";
    }
}
