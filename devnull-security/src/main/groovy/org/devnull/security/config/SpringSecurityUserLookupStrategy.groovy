package org.devnull.security.config

import org.devnull.security.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component("springSecurityUserLocationStrategy")
class SpringSecurityUserLookupStrategy implements UserLookupStrategy {
    final def log = LoggerFactory.getLogger(this.class)

    User lookupCurrentUser() {
        def principal = SecurityContextHolder.context.authentication.principal
        log.trace("Current user principal: {}", principal)
        return principal as User
    }
}