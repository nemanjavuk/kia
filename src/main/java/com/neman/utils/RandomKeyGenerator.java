package com.neman.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by nemanja.
 */
public class RandomKeyGenerator {

    private final SecureRandom random;

    public RandomKeyGenerator() {
        random = new SecureRandom();
    }

    public String createSessionKey() {
        return new BigInteger(130, random).toString(32);
    }

}