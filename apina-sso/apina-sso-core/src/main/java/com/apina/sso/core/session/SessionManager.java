package com.apina.sso.core.session;

import com.apina.sso.core.realm.RealmManager;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
@Component
@Scope("singleton")
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private CacheManager cacheManager;

    @Autowired
    private RealmManager realmManager;

    private long maxSessionIdleTime = 30;
    private long maxSessionTime = 0;

    public String createSession(String realm, String username, Map<String, String> sessionAttributes) throws Exception {
        // Generate id token for the session
        String token = generateToken();

        // Create session data item
        SessionData session = new SessionData(token, sessionAttributes);

        // Add session to cache
        getSessionCache("sessionCache").put(token, session);

        return token;
    }

    public void initializeCache() {
        logger.info("Initilizing session cache...");
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("sessionCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, SessionData.class, ResourcePoolsBuilder.heap(10)))
                .build(true);

        /*
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        for (String realmName : realmManager.getRealmNames()) {
            logger.info("Creating session cache for realm \"" + realmName + "\"");
            cacheManager.createCache(realmName,
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, SessionData.class, ResourcePoolsBuilder.heap(10))
                            .build());
        }
        */
    }

    public boolean isTokenValid(String token) {
        return getSessionCache("sessionCache").containsKey(token);
    }

    private Cache<String, SessionData> getSessionCache(String name) {
        return cacheManager.getCache(name, String.class, SessionData.class);
    }

    public String generateToken() {
        // @ TODO encode realm name and session start time to the token
        return "token" + System.nanoTime();
    }

    public long getMaxSessionIdleTime() {
        return maxSessionIdleTime;
    }

    public void setMaxSessionIdleTime(long maxSessionIdleTime) {
        this.maxSessionIdleTime = maxSessionIdleTime;
    }

    public long getMaxSessionTime() {
        return maxSessionTime;
    }

    public void setMaxSessionTime(long maxSessionTime) {
        this.maxSessionTime = maxSessionTime > 0 ? maxSessionTime : 0;
    }
}
