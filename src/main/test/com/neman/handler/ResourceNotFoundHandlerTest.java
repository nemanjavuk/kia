package com.neman.handler;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by nemanja on 8/23/14.
 */
public class ResourceNotFoundHandlerTest {

    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        httpExchange = mock(HttpExchange.class);
    }

    @Test
    public void testNotFoundResponse() {
    }
}
