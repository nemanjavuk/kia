package com.neman.server.handler;

import com.neman.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by nemanja.
 */
public class ResourceNotFoundHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        OutputStream responseBody = httpExchange.getResponseBody();
        responseHeaders.set("Content-Type", "text/plain");
        try {
            httpExchange.sendResponseHeaders(404, 0);
            responseBody.close();
        } catch (IOException ioe) {
            IOUtils.closeQuietly(responseBody);
        }
    }
}

