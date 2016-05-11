package com.apina.sso.core.session;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kari Kurillo on 10.5.2016.
 */
public class SessionCacheListener implements CacheEventListener<String, SessionData> {

    private static final Logger logger = LoggerFactory.getLogger(SessionCacheListener.class);

    @Override
    public void onEvent(CacheEvent<String, SessionData> event) {
        if (event.getType() == EventType.EXPIRED || event.getType() == EventType.EVICTED) {
            logger.info("Expired session: user=" + event.getOldValue().getUsername() + ", token=" + event.getOldValue().getToken());
        }
    }

}
