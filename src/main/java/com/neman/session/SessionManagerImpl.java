package com.neman.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nemanja on 8/22/14.
 */
public class SessionManagerImpl implements SessionManager {
    private ConcurrentHashMap<Integer, Session> sessions;
    private ConcurrentHashMap<String, Integer> userIds;

    public SessionManagerImpl() {
        sessions = new ConcurrentHashMap<Integer, Session>();
        userIds = new ConcurrentHashMap<String, Integer>();
    }


    @Override
    public String getSessionKey(Integer userId) {
        if (sessions.containsKey(userId)) {
            Session simpleSession = sessions.get(userId);
            if (!simpleSession.isExpired()) {
                return simpleSession.getSessionKey();
            } else {
                return generateNewSessionKey(userId);
            }
        } else {
            return generateNewSessionKey(userId);
        }
    }


    private String generateNewSessionKey(Integer userId) {
        String sessionKey = SessionKeyGenerator.getId();
        long now = System.currentTimeMillis();
        Session simpleSession = new SimpleSession(sessionKey, now);
        //TODO:nemanja:this should be an atomic op on both maps
        Session updatedSimpleSession = sessions.replace(userId, simpleSession);
        if (updatedSimpleSession != null) {
            userIds.replace(simpleSession.getSessionKey(), userId);
        } else {
            sessions.put(userId, simpleSession);
            userIds.put(simpleSession.getSessionKey(), userId);
        }
        return simpleSession.getSessionKey();
    }

    @Override
    public int getUserId(String sessionKey) {
        if (userIds.containsKey(sessionKey)) {
            return userIds.get(sessionKey);
        } else {
            //non existing userId for given sessionKey
            return -1;
        }
    }


}
