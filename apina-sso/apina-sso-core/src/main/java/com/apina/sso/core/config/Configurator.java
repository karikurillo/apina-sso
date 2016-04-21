package com.apina.sso.core.config;

import java.util.Map;

/**
 * Created by Kari Kurillo on 19/04/16.
 */
public interface Configurator {

    void initialize(Map<String, String> config) throws Exception;
}
