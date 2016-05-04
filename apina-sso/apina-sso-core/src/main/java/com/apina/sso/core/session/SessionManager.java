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
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

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
        String token = generateToken(realm);

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

        // @ TODO Add listener for expiring cache items

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

    public boolean logout(String token) throws Exception {
        Cache<String, SessionData> cache = getSessionCache("sessionCache");
        if (cache.containsKey(token)) {
            cache.remove(token);
            return true;
        }
        return false;
    }

    private Cache<String, SessionData> getSessionCache(String name) {
        return cacheManager.getCache(name, String.class, SessionData.class);
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
