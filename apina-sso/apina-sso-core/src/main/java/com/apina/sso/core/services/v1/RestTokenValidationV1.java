package com.apina.sso.core.services.v1;

import com.apina.sso.core.services.v1.pojos.RestResponseValidation;
import com.apina.sso.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kari Kurillo on 28/04/16.
 */
@RestController
public class RestTokenValidationV1 {

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping(value = "/v1/validate/{token}", method = RequestMethod.GET)
    public RestResponseValidation validateToken(@PathVariable(value = "token") String token) {
        return new RestResponseValidation(sessionManager.isTokenValid(token));
    }
}
