package com.neman.session;

/**
 * Created by nemanja on 8/22/14.
 */
public interface SessionManager {

    public String getSessionKey(Integer userId);

    public int getUserId(String sessionKey);
}
