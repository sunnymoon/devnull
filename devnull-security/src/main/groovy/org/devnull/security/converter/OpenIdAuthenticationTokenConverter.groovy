package org.devnull.security.converter

import org.devnull.security.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException

class OpenIdAuthenticationTokenConverter implements AuthenticationConverter {

    User convert(Authentication authentication) {
        if (authentication instanceof OpenIDAuthenticationToken) {
            def token = authentication as OpenIDAuthenticationToken
            if (token.status != OpenIDAuthenticationStatus.SUCCESS) {
                throw new BadCredentialsException("Invalid token status: ${token.status}, messsage: ${token.message}")
            }
            def user = new User(openId: token.identityUrl)
            user.email = token.attributes.find { it.name == "email" }?.values?.first()
            // TODO support more attributes
            return user
        }
        else {
            throw new InsufficientAuthenticationException("Authentication type ${authentication.class} is not supported")
        }
    }
}
