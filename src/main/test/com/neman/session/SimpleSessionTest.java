package com.neman.session;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nemanja on 8/24/14.
 */
public class SimpleSessionTest {

    @Test
    public void testSessionNotExpired() {
        long fiveMinutesAgo = System.currentTimeMillis() - 300000;
        Session testSession = new SimpleSession("dummykey", fiveMinutesAgo);
        Assert.assertFalse(testSession.isExpired());
    }

    @Test
    public void testSessionExpired() {
        long tenMinutesAgo = System.currentTimeMillis() - 6000001;
        Session testSession = new SimpleSession("dummykey", tenMinutesAgo);
        Assert.assertTrue(testSession.isExpired());
    }
}
