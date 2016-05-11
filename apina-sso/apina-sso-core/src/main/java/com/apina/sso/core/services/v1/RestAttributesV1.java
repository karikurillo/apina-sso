package com.apina.sso.core.services.v1;

import com.apina.sso.core.security.*;
import com.apina.sso.core.services.ServiceConsts;
import com.apina.sso.core.services.v1.pojos.RestResponseAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
@RestController
public class RestAttributesV1 {

    @Autowired
    private com.apina.sso.core.security.SecurityManager securityManager;

    @RequestMapping(value = "/v1/attributes/{token}", method = RequestMethod.GET)
    public RestResponseAttributes attributesPath(@PathVariable(value = "token") String token) {
        return getUserAttributes(token);
    }

    @RequestMapping(value = "/v1/attributes", method = RequestMethod.GET)
    public RestResponseAttributes attributesHeader(@RequestHeader(value = ServiceConsts.HEADER_TOKEN_V1) String token) {
        return getUserAttributes(token);
    }

    private RestResponseAttributes getUserAttributes(String token) {
        AttributesResponse attributesResponse = null;
        try {
            attributesResponse = securityManager.getUserAttributes(token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user attributes: " + e.getMessage());
        }

        if (attributesResponse != null) {
            RestResponseAttributes restResponseAttributes = new RestResponseAttributes();
            restResponseAttributes.setGroups(attributesResponse.getGroups());
            restResponseAttributes.setRoles(attributesResponse.getRoles());
            // User attributes
            restResponseAttributes.setAttributes(attributesResponse.getUserAttributes());

            return restResponseAttributes;
        }

        return null;
    }
}
