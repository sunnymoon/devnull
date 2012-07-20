package org.devnull.security.converter

import org.springframework.security.core.Authentication
import org.devnull.security.model.User

interface AuthenticationConverter {
    User convert(Authentication authentication)
}
