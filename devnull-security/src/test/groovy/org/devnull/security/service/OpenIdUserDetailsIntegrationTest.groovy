package org.devnull.security.service

import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.model.User
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException

class OpenIdUserDetailsIntegrationTest extends BaseSecurityIntegrationTest {
    @Autowired
    OpenIdUserDetailsService service

    @Test
    void danAykroydCanLogin() {
        def dan = service.loadUserByUsername('http://fake.openid.com/daykroyd') as User
        assert dan.firstName == "Dan"
        assert dan.lastName == "Aykroyd"
        assert dan.email == "daykroyd@ghostbusters.com"
        assert dan.accountNonExpired
        assert dan.accountNonLocked
        assert dan.enabled
    }

    @Test(expected = UsernameNotFoundException)
    void rickMoranisCanNotLogin() {
        def dan = service.loadUserByUsername('http://fake.openid.com/rmoranis')
    }
}
