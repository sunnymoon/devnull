package org.devnull.security.service

import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.model.User
import org.devnull.security.spring.OpenIdUserDetailsService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException

public class SecurityServiceIntegrationTest extends BaseSecurityIntegrationTest {

    @Autowired
    SecurityService service

    @Test
    void findUserByOpenIdShouldFindDanAykroyd() {
        def dan = service.findUserByOpenId('http://fake.openid.com/daykroyd') as User
        assert dan.firstName == "Dan"
        assert dan.lastName == "Aykroyd"
        assert dan.email == "daykroyd@ghostbusters.com"
        assert dan.accountNonExpired
        assert dan.accountNonLocked
        assert dan.enabled
    }

    @Test()
    void findUserByOpenIdShouldNotFindRickMoranis() {
        def rick = service.findUserByOpenId('http://fake.openid.com/rmoranis')
        assert !rick
    }

}
