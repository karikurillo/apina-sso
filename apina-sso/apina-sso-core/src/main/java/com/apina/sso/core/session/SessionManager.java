package com.apina.sso.core.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@Scope("singleton")
public class SessionManager {

    public String generateToken() {
        return "token" + System.nanoTime();
    }
}
