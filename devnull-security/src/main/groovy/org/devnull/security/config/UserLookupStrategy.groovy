package org.devnull.security.config

import org.devnull.security.model.User
import org.springframework.data.domain.AuditorAware

public interface UserLookupStrategy extends AuditorAware<User> {
    User lookupCurrentUser()
    void reAuthenticateCurrentUser()
}