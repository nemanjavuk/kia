package com.neman.server.handler;

import com.neman.domain.HighScores;
import com.neman.session.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Created by nemanja.
 */
public interface RequestRouter extends HttpHandler{

    public HttpHandler createHandler(HttpExchange httpExchange, SessionManager sessionManager, HighScores highScores);
}
