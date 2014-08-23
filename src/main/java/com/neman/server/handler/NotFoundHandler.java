package com.neman.server.handler;

import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class NotFoundHandler extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        System.out.println("going to not found handler");
        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        responseHeaders.set("Content-Type", "text/plain");
        try {
            httpExchange.sendResponseHeaders(404, 0);
            responseBody.close();
        } catch (IOException ioe) {
            IOUtils.closeQuietly(responseBody);
        }
        //TODO:nemanja:some appropriate message but I believe the status code is enough for now
    }
}

