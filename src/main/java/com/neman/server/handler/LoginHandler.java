package com.neman.server.handler;

import com.neman.session.SessionManager;
import com.neman.session.SessionManagerImpl;
import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class LoginHandler extends AbstractHandler {

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
        SessionManager sessionManager = SessionManagerImpl.getInstance();
        return sessionManager.getSessionKey(userId);
    }

}
