package com.neman.server.handler;

import com.neman.data.HighScores;
import com.neman.session.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Created by nemanja on 8/24/14.
 */
public interface RequestRouter extends HttpHandler{

    public HttpHandler createHandler(HttpExchange httpExchange, SessionManager sessionManager, HighScores highScores);
}
