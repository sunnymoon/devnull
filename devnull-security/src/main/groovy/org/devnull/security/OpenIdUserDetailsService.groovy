package org.devnull.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

class OpenIdUserDetailsService implements UserDetailsService {
    def mockUsers = []
    UserDetails loadUserByUsername(String username) {
        if (mockUsers.contains(username)) {
            return new User(username, "notapassword", [new SimpleGrantedAuthority("ROLE_USER")] as Set)
        }
        throw new UsernameNotFoundException("Unable to locate user with open id token :${username}")
    }
}
