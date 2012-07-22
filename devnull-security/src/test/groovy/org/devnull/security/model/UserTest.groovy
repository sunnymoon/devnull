package org.devnull.security.model

import org.junit.Test
import org.junit.Before;

public class UserTest {

    User user

    @Before
    void createUser() {
        user = new User(id:1, openId:"http://fake.openid.com", firstName: "Billy", lastName: "Bob", email:"bob@aol.com")
    }

    @Test
    void setOpenIdShouldBeImmutableOnceSet() {
        user.openId = "http://hacked.openid.com"
        assert user.openId == "http://fake.openid.com"
    }
    
    @Test
    void seIdShouldBeImmutableOnceSet() {
        user.id = 1234
        assert user.id == 1
    }

    @Test
    void addToRolesShouldNotDuplicateRoles() {
        def roles = [new Role(id: 1, name: "a"), new Role(id: 2, name: "b"), new Role(id: 3, name: "c")]
        user.roles = roles
        def duplicateRole = new Role(id: 2, name: "b")
        user.addToRoles(duplicateRole)
        assert user.roles.size() == 3
        assert !user.roles.find { it.is(duplicateRole)}
    }

    @Test
    void addToRolesShouldAddNewRoles() {
        def roles = [new Role(id: 1, name: "a"), new Role(id: 2, name: "b"), new Role(id: 3, name: "c")]
        user.roles = roles
        def newRole = new Role(id: 4, name: "d")
        user.addToRoles(newRole)
        assert user.roles.size() == 4
        assert user.roles.contains(newRole)
    }
}
