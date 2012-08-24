package org.devnull.security.spring

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

import static org.mockito.Mockito.*

class MockAuthenticationManager implements AuthenticationManager {
    Authentication authenticate(Authentication authentication) {
        return mock(Authentication)
    }
}
