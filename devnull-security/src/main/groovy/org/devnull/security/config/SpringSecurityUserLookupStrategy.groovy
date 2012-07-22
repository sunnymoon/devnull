package org.devnull.security.config

import org.devnull.security.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component("springSecurityUserLocationStrategy")
class SpringSecurityUserLookupStrategy implements UserLookupStrategy {
    final def log = LoggerFactory.getLogger(this.class)

    @Autowired
    AuthenticationManager authenticationManager

    User lookupCurrentUser() {
        def principal = SecurityContextHolder.context?.authentication?.principal
        log.trace("Current user principal: {}", principal)
        return principal as User
    }

    void reAuthenticateCurrentUser() {
        log.info("Re-Authenticating..")
        def authentication = SecurityContextHolder.context.authentication
        def response = authenticationManager.authenticate(authentication)
        SecurityContextHolder.getContext().setAuthentication(response);
        log.info("Re-Authentication response: {}", response)
    }

}