package com.neman.session;

import java.util.Random;

/**
 * Created by nemanja on 8/22/14.
 */
public class SessionKeyGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 10;
    private static final Random random = new Random();

    public static String getId() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            builder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }
}
