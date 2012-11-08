package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User
import org.devnull.security.model.UserRevision
import org.junit.Before
import org.junit.Test

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

import static org.mockito.Mockito.*

class AuditServiceImplTest {

    AuditServiceImpl service

    @Before
    void createService() {
        service = new AuditServiceImpl(securityService: mock(SecurityService), entityManagerFactory: mock(EntityManagerFactory))
    }

    @Test
    void shouldFindDistinctListOfUsersWhoCreatedRevisions() {
        def audits = [
                new AuditRevision(revision: new UserRevision(modifiedBy: "userA")),
                new AuditRevision(revision: new UserRevision(modifiedBy: "userB")),
                new AuditRevision(revision: new UserRevision(modifiedBy: "userB")),
                new AuditRevision(revision: new UserRevision(modifiedBy: "userA")),
                new AuditRevision(revision: new UserRevision(modifiedBy: "userB")),
        ]
        def userA = new User(userName: "userA")
        def userB = new User(userName: "userB")
        when(service.securityService.findByUserName("userA")).thenReturn(userA)
        when(service.securityService.findByUserName("userB")).thenReturn(userB)
        def users = service.collectUsersFromRevisions(audits)
        assert users == ["userA": userA, "userB": userB]
    }


    @Test
    void shouldCloseEntityManagerWhenExceptionIsThrown() {
        def mockManager = mock(EntityManager)
        def exception = null
        when(service.entityManagerFactory.createEntityManager()).thenReturn(mockManager)
        try {
            service.doWithEntityManager { manager ->
                throw new RuntimeException("test")
            }
        } catch (RuntimeException e) {
            exception = e
        }
        verify(mockManager).close()
        assert exception.message == "test"
    }
}
