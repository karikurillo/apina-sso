package com.apina.sso.core.session;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kari Kurillo on 10.5.2016.
 */
public class SessionCacheListener implements CacheEventListener<String, SessionData> {

    private static final Logger logger = LoggerFactory.getLogger(SessionCacheListener.class);

    @Override
    public void onEvent(CacheEvent<String, SessionData> event) {
        logger.debug("Received event from session cache: " + event.getType());
    }

}
