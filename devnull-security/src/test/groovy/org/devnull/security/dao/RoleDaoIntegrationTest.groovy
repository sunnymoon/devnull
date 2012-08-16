package org.devnull.security.dao

import org.devnull.security.BaseSecurityIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort

class RoleDaoIntegrationTest extends BaseSecurityIntegrationTest {
    @Autowired
    RoleDao roleDao

    @Test
    void listFindsAllRoles() {
        def roles = roleDao.findAll(new Sort("name")) as List
        assert roles.size() == 4
        assert roles[0].name == "ROLE_ADMIN"
        assert roles[0].description == "Admin"
        assert roles[1].name == "ROLE_GUEST"
        assert roles[1].description == "Guest"
        assert roles[2].name == "ROLE_SYSTEM_ADMIN"
        assert roles[2].description == "Sysadmin"
        assert roles[3].name == "ROLE_USER"
        assert roles[3].description == "User"
    }

    @Test
    void findByNameShouldReturnCorrectRecord() {
        def guest = roleDao.findByName("ROLE_GUEST")
        assert guest.name == "ROLE_GUEST"
        def sysAdmin = roleDao.findByName("ROLE_SYSTEM_ADMIN")
        assert sysAdmin.name == "ROLE_SYSTEM_ADMIN"
    }
}
