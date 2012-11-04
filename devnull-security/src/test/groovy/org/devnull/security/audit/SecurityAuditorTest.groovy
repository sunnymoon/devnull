package org.devnull.security.audit

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class SecurityAuditorTest {

    SecurityAuditor auditor

    @Before
    void createAuditor() {
        auditor = new SecurityAuditor(userLookupStrategy: mock(UserLookupStrategy))
    }

    @Test
    void shouldLoookupCurrentUser() {
        def expected = new User(id: 1)
        when(auditor.userLookupStrategy.lookupCurrentUser()).thenReturn(expected)
        auditor.currentAuditor.is(expected)
    }
}
