package com.neman.session;

/**
 * Created by nemanja.
 */
public interface Session {
    public String getSessionKey();

    public boolean isExpired();
}
