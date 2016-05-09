package com.apina.sso.core.services.v1;

import com.apina.sso.core.security.SecurityManager;
import com.apina.sso.core.services.v1.pojos.RestResponseLogout;
import com.apina.sso.core.services.v1.pojos.RestResponseValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
public class RestLogoutV1 {

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "/v1/logout/{token}", method = RequestMethod.GET)
    public RestResponseLogout logoutPath(@PathVariable(value = "token") String token) {
        return new RestResponseLogout(securityManager.logoutToken(token));
    }

    @RequestMapping(value = "/v1/logout", method = RequestMethod.GET)
    public RestResponseLogout logoutHeader(@RequestHeader(value="X-ApinaSSO-Token") String token) {
        return new RestResponseLogout(securityManager.logoutToken(token));
    }
}
