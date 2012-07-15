package org.devnull.security.service

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import org.devnull.security.model.User
import org.devnull.test.BaseDataIntegrationTest

class OpenIdUserDetailsIntegrationTest extends BaseDataIntegrationTest {
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
}
