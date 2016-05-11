package com.apina.sso.core.session;

import com.apina.sso.core.realm.RealmManager;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Eviction;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.*;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.expiry.Expiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
@Scope("singleton")
public class SessionManager {

    private static final String CACHE_NAME = "sessionCache";

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private CacheManager cacheManager;

    @Autowired
    private RealmManager realmManager;

    private long maxSessionIdleTime = 30;
    private long maxSessionTime = 0;

    public SessionManager() {
        logger.info("Initializing SessionManager...");
    }

    public String createSession(String realm, String username, Map<String, String> sessionAttributes) throws Exception {
        // Generate id token for the session
        String token = generateToken(realm);

        // Create session data item
        SessionData session = new SessionData(realm, username, token, sessionAttributes);

        // Add session to cache
        getSessionCache(SessionManager.CACHE_NAME).put(token, session);

        return token;
    }

    public void initializeCache() {
        logger.info("Initializing session cache...");
        // For now the session lives forever
        // Note: If both expirations are set, only the later one will be valid
        CacheConfiguration<String, SessionData> cacheConfiguration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, SessionData.class, ResourcePoolsBuilder.heap(10))
                        // Session idle time - expire after a fixed duration following their last access time
                        .withExpiry(Expirations.timeToIdleExpiration(new Duration(maxSessionIdleTime, TimeUnit.MINUTES)))
                        // TODO Session max time - expire after a fixed duration following their creation
                        //.withExpiry(Expirations.timeToLiveExpiration(maxSessionTime > 0 ? new Duration(maxSessionTime, TimeUnit.MINUTES) : Duration.INFINITE))
                        .build();

        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(SessionManager.CACHE_NAME, cacheConfiguration)
                .build(true);

        // Listen for expiration events
        cacheManager.getCache(SessionManager.CACHE_NAME, String.class, SessionData.class)
                .getRuntimeConfiguration()
                .registerCacheEventListener(new SessionCacheListener(), EventOrdering.UNORDERED, EventFiring.ASYNCHRONOUS, EventType.EXPIRED);

    }

    public boolean isTokenValid(String token) {
        return getSessionCache(SessionManager.CACHE_NAME).containsKey(token);
    }

    public boolean logout(String token) throws Exception {
        Cache<String, SessionData> cache = getSessionCache(SessionManager.CACHE_NAME);
        if (cache.containsKey(token)) {
            cache.remove(token);
            return true;
        }
        return false;
    }

    private Cache<String, SessionData> getSessionCache(String name) {
        return cacheManager.getCache(name, String.class, SessionData.class);
    }

    public SessionInfo getSessionInfo(String token) {
        SessionInfo info = new SessionInfo(token);
        SessionData session = getSessionCache(SessionManager.CACHE_NAME).get(token);
        if (session != null) {
            info.setSessionValid(true);
            info.setRealm(session.getRealm());
            info.setUsername(session.getUsername());
            info.setSessionAttributes(session.getAttributes());
        }

        return info;
    }

    public String generateToken(String realm) {
        String id = UUID.randomUUID().toString();
        byte[] encodedBytes = Base64Utils.encode((id + ":" + realm).getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    private String getRealmFromToken(String token) {
        byte[] decodedBytes = Base64Utils.decode(token.getBytes());
        String decodedToken = new String(decodedBytes, Charset.forName("UTF-8"));
        return decodedToken.substring(decodedToken.indexOf(":"));
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
