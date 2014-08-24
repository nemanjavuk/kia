package com.neman.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by nemanja on 8/22/14.
 */
public final class RandomKeyGenerator {

    private RandomKeyGenerator() {
    }

    private static final SecureRandom random = new SecureRandom();

    public static String createSessionKey() {
        return new BigInteger(130, random).toString(32);
    }

}