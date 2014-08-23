package com.neman.session;

/**
 * Created by nemanja on 8/22/14.
 */
public class Session {
    private final static long TEN_MINUTES = 600000;

    private String sessionKey;
    private long startTime;

    public Session(String sessionKey, long startTime) {
        this.sessionKey = sessionKey;
        this.startTime = startTime;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public boolean hasExpired() {
        long now = System.currentTimeMillis();
        return (startTime + TEN_MINUTES) < now;
    }
}
