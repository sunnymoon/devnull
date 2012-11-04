package org.devnull.security.audit

import org.devnull.security.model.User
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.devnull.security.config.UserLookupStrategy

@Component("securityAuditor")
class SecurityAuditor implements AuditorAware<User> {

    @Autowired
    UserLookupStrategy userLookupStrategy

    User getCurrentAuditor() {
        return userLookupStrategy.lookupCurrentUser()
    }
}
