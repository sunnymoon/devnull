package org.devnull.security.service

import org.devnull.security.model.User

public interface SecurityService {
    User registerNewOpenIdUser(String openIdToken, User user)
}