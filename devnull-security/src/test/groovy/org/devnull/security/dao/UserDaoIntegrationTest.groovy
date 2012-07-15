package org.devnull.security.dao

import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import org.devnull.security.test.BaseIntegrationTest
import org.devnull.security.model.User

class UserDaoIntegrationTest extends BaseIntegrationTest {
    @Autowired
    UserDao dao

    @Test
    void createMissingGhostBusterShouldPersistRecord() {
        def user = new User(openId: 'http://fake.openid.com/ehudson', firstName: 'Ernie', lastName: 'Hudson', email: 'ehudson@ghostbusters.com')
        def ernie = dao.save(user)
        assert ernie.openId == 'http://fake.openid.com/ehudson'
        assert ernie.accountNonExpired
        assert ernie.accountNonLocked
        assert ernie.enabled
        assert ernie.id > 3
    }

    @Test
    void findByOpenIdShouldReturnCorrectRecord() {
        def user = dao.findByOpenId("http://fake.openid.com/daykroyd")
        assert user.openId == "http://fake.openid.com/daykroyd"
    }
}
