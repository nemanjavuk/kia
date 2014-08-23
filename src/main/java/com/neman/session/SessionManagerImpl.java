package com.neman.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nemanja on 8/22/14.
 */
public class SessionManagerImpl implements SessionManager {
    private static SessionManagerImpl instance;
    private ConcurrentHashMap<Integer, Session> sessions;
    private ConcurrentHashMap<String, Integer> userIds;

    private SessionManagerImpl() {
        sessions = new ConcurrentHashMap<Integer, Session>();
        userIds = new ConcurrentHashMap<String, Integer>();
    }

    //double check approach to creating Singleton
    public static SessionManagerImpl getInstance() {
        if (null == instance) {
            synchronized (SessionManager.class) {
                if (null == instance) {
                    instance = new SessionManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String getSessionKey(Integer userId) {
        if (sessions.containsKey(userId)) {
            Session session = sessions.get(userId);
            if (!session.hasExpired()) {
                return session.getSessionKey();
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
        Session session = new Session(sessionKey, now);
        //TODO:nemanja:this should be an atomic op on both maps
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
