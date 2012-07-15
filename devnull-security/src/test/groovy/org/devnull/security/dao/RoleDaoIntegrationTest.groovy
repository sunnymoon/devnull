package org.devnull.security.dao

import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import org.springframework.data.domain.Sort
import org.devnull.test.BaseDataIntegrationTest

class RoleDaoIntegrationTest extends BaseDataIntegrationTest {
    @Autowired
    RoleDao roleDao

    @Test
    void listFindsAllRoles() {
        def roles = roleDao.findAll(new Sort("name"))
        assert roles.size() == 3
        assert roles[0].name == "ROLE_ADMIN"
        assert roles[1].name == "ROLE_SYSTEM_ADMIN"
        assert roles[2].name == "ROLE_USER"
    }
}
