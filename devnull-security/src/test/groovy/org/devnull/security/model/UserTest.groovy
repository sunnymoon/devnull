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
}
