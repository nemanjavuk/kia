package com.neman.session;

/**
 * Created by nemanja on 8/22/14.
 */
public class SimpleSession implements Session {
    //TODO:nemanja:extract this up somewhere
    private final static long SESSION_DURATION = 600000; //10 minutes

    private String sessionKey;
    private long startTime;

    public SimpleSession(String sessionKey, long startTime) {
        this.sessionKey = sessionKey;
        this.startTime = startTime;
    }

    @Override
    public String getSessionKey() {
        return sessionKey;
    }

    @Override
    public boolean isExpired() {
        long now = System.currentTimeMillis();
        return (startTime + SESSION_DURATION) < now;
    }
}
