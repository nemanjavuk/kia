package com.neman.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nemanja on 8/24/14.
 */
public class RandomKeyGeneratorTest {
    @Test
    public void testGenerateKeyLengthIs() {
        String key = RandomKeyGenerator.createSessionKey();
        Assert.assertNotNull(key);
        Assert.assertEquals(26, key.length());
    }

    @Test
    public void testSequentiallyGeneratedKeysAreNotTheSame() {
        String key1 = RandomKeyGenerator.createSessionKey();
        String key2 = RandomKeyGenerator.createSessionKey();

        Assert.assertNotNull(key1);
        Assert.assertNotNull(key2);

        Assert.assertFalse(key1.equals(key2));
    }
}
