package com.neman.session;

import com.neman.utils.RandomKeyGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nemanja.
 */
public class SessionManagerImpl implements SessionManager {
    //userId -> session mapping
    private ConcurrentHashMap<Integer, Session> sessions;
    //sessionKey -> userId mapping
    private ConcurrentHashMap<String, Integer> userIds;

    private RandomKeyGenerator keyGenerator;

    public SessionManagerImpl(RandomKeyGenerator keyGenerator) {
        sessions = new ConcurrentHashMap<Integer, Session>();
        userIds = new ConcurrentHashMap<String, Integer>();
        this.keyGenerator = keyGenerator;
    }


    @Override
    public String getExistingSessionKey(Integer userId) {
        if (sessions.containsKey(userId)) {
            Session session = sessions.get(userId);
            if (!session.isExpired()) {
                return session.getSessionKey();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getNewSessionKey(Integer userId) {
        String sessionKey = keyGenerator.createSessionKey();
        long now = System.currentTimeMillis();
        Session session = new SimpleSession(sessionKey, now);
        Session updatedSession = sessions.replace(userId, session);
        if (updatedSession != null) {
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
