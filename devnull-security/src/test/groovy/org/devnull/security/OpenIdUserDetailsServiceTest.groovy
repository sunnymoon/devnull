package org.devnull.security

import org.junit.Test

class OpenIdUserDetailsServiceTest {
    @Test
    void loadUserByNameShouldConstructUser() {
        def service = new OpenIdUserDetailsService(mockUsers: ['abc', '123'])
        def user = service.loadUserByUsername('abc')
        assert user.username == "abc"
        def authorities = user.authorities.collect { it.role }
        assert authorities.size() == 1
        assert authorities.first() == "ROLE_USER"
    }
}
