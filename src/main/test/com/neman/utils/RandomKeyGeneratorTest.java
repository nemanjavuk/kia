package com.neman.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nemanja.
 */
public class RandomKeyGeneratorTest {

    private RandomKeyGenerator keyGenerator;

    @Before
    public void setUp() {
        keyGenerator = new RandomKeyGenerator();
    }

    @Test
    public void testGenerateKeyLengthIs() {
        String key = keyGenerator.createSessionKey();
        Assert.assertNotNull(key);
        Assert.assertEquals(26, key.length());
    }

    @Test
    public void testSequentiallyGeneratedKeysAreNotTheSame() {
        String key1 = keyGenerator.createSessionKey();
        String key2 = keyGenerator.createSessionKey();

        Assert.assertNotNull(key1);
        Assert.assertNotNull(key2);

        Assert.assertFalse(key1.equals(key2));
    }
}
