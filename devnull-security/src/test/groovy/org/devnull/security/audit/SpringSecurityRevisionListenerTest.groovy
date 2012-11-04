package org.devnull.security.audit

import org.junit.Before
import org.junit.Test
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.TestingAuthenticationToken
import org.devnull.security.model.User
import org.devnull.security.model.UserRevision

class SpringSecurityRevisionListenerTest {
    SpringSecurityRevisionListener listener

    @Before
    void createListener() {
        listener = new SpringSecurityRevisionListener()
    }

    @Test
    void shouldLookupUserFromSpringSecurityContext() {
        def revision = new UserRevision()
        def expected = new User(userName: "testUser")
        SecurityContextHolder.context?.authentication = new TestingAuthenticationToken(expected, "*********")
        listener.newRevision(revision)
        assert revision.userName == "testUser"
    }
}
