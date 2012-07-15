package org.devnull.security.dao

import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import org.devnull.security.test.BaseIntegrationTest

class OpenIdUserDaoIntegrationTest extends BaseIntegrationTest {
    @Autowired
    OpenIdUserDao dao

    @Test
    void findOneShouldFindCorrectRecord() {
        def user = dao.findOne("http://fake.openid.com/1")
        assert user.id == "http://fake.openid.com/1"
    }
}
