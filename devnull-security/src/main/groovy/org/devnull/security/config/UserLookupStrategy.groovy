package org.devnull.security.config

import org.devnull.security.model.User

public interface UserLookupStrategy {
    User lookupCurrentUser()
    void reAuthenticateCurrentUser()
}