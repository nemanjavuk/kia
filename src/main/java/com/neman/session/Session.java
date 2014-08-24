package com.neman.session;

/**
 * Created by nemanja on 8/24/14.
 */
public interface Session {
    public String getSessionKey();

    public boolean isExpired();
}
