package com.apina.sso.core.services.v1;

import com.apina.sso.core.realm.RealmManager;
import com.apina.sso.core.security.SecurityManager;
import com.apina.sso.core.services.v1.pojos.RestResponseAuthentication;
import com.apina.sso.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class RestAuthenticationV1 {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "/v1/test", method = RequestMethod.GET)
    public String message() {
        return "Hello World!";
    }

    @RequestMapping(value = "/v1/authenticate", method = RequestMethod.GET)
    public RestResponseAuthentication authenticate() {
        return authenticateUser(RealmManager.ROOT_REALM, "", "");
    }

    @RequestMapping(value = "/v1/{realm}/authenticate", method = RequestMethod.GET)
    public RestResponseAuthentication authenticate(@PathVariable(value = "realm") String realm) {
        return authenticateUser(realm, "", "");
    }

    private RestResponseAuthentication authenticateUser(String realm, String username, String password) {
        // @TODO Add support for custom authentication parameters

        try {
            securityManager.authenticateUser("", "", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        RestResponseAuthentication response = new RestResponseAuthentication(sessionManager.generateToken(), "All is good");
        response.addAttribute("custom-attribute-1", "custom-value-1");
        response.addAttribute("realm", realm);
        return response;
    }
}
