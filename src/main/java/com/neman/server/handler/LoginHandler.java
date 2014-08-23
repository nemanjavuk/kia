package com.neman.server.handler;

import com.neman.session.SessionManager;
import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginHandler implements HttpHandler {

    private final SessionManager sessionManager;

    public LoginHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String sessionKey = login(httpExchange);
        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        try {
            responseHeaders.set("Content-Type", "text/plain");
            httpExchange.sendResponseHeaders(200, sessionKey.getBytes().length);
            responseBody.write(sessionKey.getBytes());
            responseBody.close();
        } catch (IOException ioe) {
            IOUtils.closeQuietly(responseBody);
        }
    }

    private String login(HttpExchange httpExchange) {
        int userId = getUserId(httpExchange);
        return sessionManager.getSessionKey(userId);
    }

    public int getUserId(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        Pattern pattern = Pattern.compile("/(?<id>\\d+)/login");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            String id = matcher.group("id");
            try {
                return Integer.parseInt(id);
            } catch (NumberFormatException nfe) {
                return -1;
            }
        }
        return -1;
    }

}
