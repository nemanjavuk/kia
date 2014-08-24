package com.neman.server.handler;

import com.neman.domain.HighScores;
import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighScoresHandler implements HttpHandler {

    private final HighScores highScores;

    public HighScoresHandler(HighScores highScores) {
        this.highScores = highScores;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        try {
            responseHeaders.set("Content-Type", "text/plain");

            int level = getLevel(httpExchange);
            if (level != -1) {
                String csv = highScores.getHighScoresCSV(level, 15);
                if (csv.isEmpty()) {
                    httpExchange.sendResponseHeaders(204, -1);
                } else {
                    httpExchange.sendResponseHeaders(200, csv.getBytes().length);
                }
                responseBody.write(csv.getBytes());
                responseBody.close();
            } else {
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

        Pattern pattern = Pattern.compile("/(?<level>\\d+)/highscorelist");
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
}
