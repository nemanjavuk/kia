package com.neman.session;

import com.neman.utils.RandomKeyGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by nemanja on 8/24/14.
 */
public class SessionManagerImplTest {

    private SessionManager sessionManager;
    private RandomKeyGenerator keyGenerator;

    @Before
    public void setUp() {
        keyGenerator = mock(RandomKeyGenerator.class);
        sessionManager = new SessionManagerImpl(keyGenerator);
    }

    @Test
    public void testGetNewSessionKeyForUser() {
        given(keyGenerator.createSessionKey()).willReturn("fdsafdsafasd");
        Assert.assertEquals("fdsafdsafasd", sessionManager.getNewSessionKey(1234));
    }

    @Test
    public void testTwiceInARowGeneratesSessionKeyOnce() {
        given(keyGenerator.createSessionKey()).willReturn("fdsafdsafasd");
        sessionManager.getNewSessionKey(1234);
        sessionManager.getExistingSessionKey(1234);
        verify(keyGenerator, times(1)).createSessionKey();
    }
}
