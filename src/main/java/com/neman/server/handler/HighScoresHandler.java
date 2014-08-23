package com.neman.server.handler;

import com.neman.data.HighScores;
import com.neman.data.HighScoresImpl;
import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighScoresHandler extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        try {
            responseHeaders.set("Content-Type", "text/plain");

            int level = getLevel(httpExchange);
            if (level != -1) {
                HighScores hs = HighScoresImpl.INSTANCE;
                String csv = hs.getHighScoresCSV(level, 15);
                httpExchange.sendResponseHeaders(200, csv.getBytes().length);
                responseBody.write(csv.getBytes());
                responseBody.close();
            } else {
                System.out.println("something bad");
                httpExchange.sendResponseHeaders(400, 0);
                responseBody.close();
            }
        } catch (IOException ioe) {
            IOUtils.closeQuietly(responseBody);
        }
    }

    @Override
    public int getLevel(HttpExchange httpExchange) {
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