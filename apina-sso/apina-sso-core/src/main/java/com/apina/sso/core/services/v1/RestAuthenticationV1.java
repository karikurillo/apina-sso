package com.apina.sso.core.services.v1;

import com.apina.sso.core.services.v1.pojos.RestResponseAuthentication;
import com.apina.sso.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/v1/test", method = RequestMethod.GET)
    public String message() {
        return "Hello World!";
    }

    @RequestMapping(value = "/v1/authenticate", method = RequestMethod.GET)
    public RestResponseAuthentication authenticate() {
        RestResponseAuthentication response = new RestResponseAuthentication(sessionManager.generateToken(), "All is good");
        response.addAttribute("custom-attribute-1", "custom-value-1");
        return response;
    }

}
