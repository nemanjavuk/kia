package com.neman.handler;

import com.neman.data.HighScores;
import com.neman.server.handler.*;
import com.neman.session.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HttpHandlerFactoryTest {

    private HttpHandlerFactory factory;
    private SessionManager sessionManager;
    private HighScores highScores;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        factory = new HttpHandlerFactory();
        httpExchange = mock(HttpExchange.class);
        sessionManager = mock(SessionManager.class);
        highScores = mock(HighScores.class);
    }

    @Test
    public void testLoginURIReturnsLoginHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/666999/login"));
        given(httpExchange.getRequestMethod()).willReturn("GET");

        HttpHandler httpHandler = factory.createHandler(httpExchange, sessionManager, highScores);
        Assert.assertTrue(httpHandler instanceof LoginHandler);
    }

    @Test
    public void testScoreURIReturnsInsertScoreHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/66/score?sessionkey=SESSIONKEY"));
        given(httpExchange.getRequestMethod()).willReturn("POST");

        HttpHandler httpHandler = factory.createHandler(httpExchange, sessionManager, highScores);
        Assert.assertTrue(httpHandler instanceof InsertScoreHandler);
    }

    @Test
    public void testHighScoreURIReturnsHighScoresHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/66/highscorelist"));
        given(httpExchange.getRequestMethod()).willReturn("GET");

        HttpHandler httpHandler = factory.createHandler(httpExchange, sessionManager, highScores);
        Assert.assertTrue(httpHandler instanceof HighScoresHandler);
    }

    @Test
    public void testNoneMatchingURIReturnsNotFoundHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/1234/something/smells/wrong"));
        given(httpExchange.getRequestMethod()).willReturn("POST");

        HttpHandler httpHandler = factory.createHandler(httpExchange, sessionManager, highScores);
        Assert.assertTrue(httpHandler instanceof ResourceNotFoundHandler);
    }
}
