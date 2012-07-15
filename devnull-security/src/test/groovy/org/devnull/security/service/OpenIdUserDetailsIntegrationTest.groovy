package org.devnull.security.service

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import org.devnull.security.model.OpenIdUser
import org.devnull.security.test.BaseIntegrationTest

class OpenIdUserDetailsIntegrationTest extends BaseIntegrationTest{
    @Autowired
    OpenIdUserDetailsService service

    @Test
    void danAykroydCanLogin() {
        def user = service.loadUserByUsername('http://fake.openid.com/3') as OpenIdUser
        assert user.firstName == "Dan"
        assert user.lastName == "Aykroid"
        assert user.email == "daykroid@ghostbusters.com"
    }
}
