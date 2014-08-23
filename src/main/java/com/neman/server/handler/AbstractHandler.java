package com.neman.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractHandler implements HttpHandler {

    public String getSessionKey(HttpExchange httpExchange) {
        return getParam(httpExchange, "sessionkey");
    }

    public int getScore(HttpExchange httpExchange) {
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
