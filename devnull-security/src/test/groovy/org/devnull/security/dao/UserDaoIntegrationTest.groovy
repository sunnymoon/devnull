package org.devnull.security.dao

import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.model.User
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort

class UserDaoIntegrationTest extends BaseSecurityIntegrationTest {
    @Autowired
    UserDao dao

    @Autowired
    RoleDao roleDao

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

    @Test
    void saveShouldPersistAddedRoles() {
        def bill = dao.findByOpenId("http://fake.openid.com/bmurray")
        assert bill.roles.size() == 1
        assert bill.roles.first().name == "ROLE_USER"
        bill.roles << roleDao.findByName("ROLE_SYSTEM_ADMIN")
        dao.save(bill)

        bill = dao.findByOpenId("http://fake.openid.com/bmurray")
        assert bill.roles.size() == 2
        assert bill.roles.find { it.name == "ROLE_USER" }
        assert bill.roles.find { it.name == "ROLE_SYSTEM_ADMIN" }
    }

    @Test
    void saveShouldRemoveRoleWithoutCascading() {
        def bill = dao.findByOpenId("http://fake.openid.com/bmurray")
        assert bill.roles.size() == 1
        assert bill.roles.first().name == "ROLE_USER"

        bill.roles.remove(0)
        dao.save(bill)

        bill = dao.findByOpenId("http://fake.openid.com/bmurray")
        assert bill.roles.size() == 0
        assert roleDao.findByName("ROLE_USER")
    }

    @Test
    void deleteShouldNotCascadeToRoles() {
        def bill = dao.findByOpenId("http://fake.openid.com/bmurray")
        assert bill.roles.size() == 1
        assert bill.roles.first().name == "ROLE_USER"
        dao.delete(bill.id)
        assert roleDao.findByName("ROLE_USER")
    }

    @Test
    void listAllUsersShouldFindAllRecordsInCorrectOrder() {
        def results = dao.findAll(new Sort("lastName")) as List
        assert results.size() == 3
        assert results[0].lastName == "Aykroyd"
        assert results[1].lastName == "Murray"
        assert results[2].lastName == "Ramis"
    }
}
