package com.neman.session;

import com.neman.utils.RandomKeyGenerator;

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
            Session session = sessions.get(userId);
            if (!session.isExpired()) {
                return session.getSessionKey();
            } else {
                return generateNewSessionKey(userId);
            }
        } else {
            return generateNewSessionKey(userId);
        }
    }


    private String generateNewSessionKey(Integer userId) {
        String sessionKey = RandomKeyGenerator.createSessionKey();
        long now = System.currentTimeMillis();
        Session session = new SimpleSession(sessionKey, now);
        //TODO:nemanja:this should be an atomic op on both maps
        Session updatedSimpleSession = sessions.replace(userId, session);
        if (updatedSimpleSession != null) {
            userIds.replace(session.getSessionKey(), userId);
        } else {
            sessions.put(userId, session);
            userIds.put(session.getSessionKey(), userId);
        }
        return session.getSessionKey();
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
