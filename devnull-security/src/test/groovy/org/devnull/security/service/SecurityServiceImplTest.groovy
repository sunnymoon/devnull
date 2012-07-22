package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.RoleDao
import org.devnull.security.dao.UserDao
import org.devnull.security.model.Role
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.*

public class SecurityServiceImplTest {
    SecurityServiceImpl service
    User currentUser

    @Before
    void createService() {
        service = new SecurityServiceImpl(
                userDao: mock(UserDao),
                roleDao: mock(RoleDao),
                userLookupStrategy: mock(UserLookupStrategy)
        )
        currentUser = new User(id: 20314, openId: "http://test.openid.com", firstName: "John", lastName: "Doe", email: "jdoe@test.com")
        currentUser.roles = [new Role(id: 1, name: "a"), new Role(id: 2, name: "b"), new Role(id: 3, name: "c")]
        when(service.userLookupStrategy.lookupCurrentUser()).thenReturn(currentUser)
    }

    @Test
    void createNewUserShouldAddRolesToUserAndSave() {
        def mockUser = new User()
        def roles = ["a", "b"]

        when(service.userDao.save(mockUser)).thenReturn(mockUser)
        when(service.roleDao.findByName("a")).thenReturn(new Role(id: 1, name: "a"))
        when(service.roleDao.findByName("b")).thenReturn(new Role(id: 1, name: "b"))
        def result = service.createNewUser(mockUser, roles)
        assert result
        roles.each { roleName ->
            verify(service.roleDao).findByName(roleName)
            assert result.roles.find { it.name == roleName }
        }
    }

    @Test
    void findRoleByNameShouldReturnResultFromDao() {
        def expected = new Role(id: 1, name: "a")
        when(service.findRoleByName("a")).thenReturn(expected)
        def result = service.findRoleByName("a")
        assert result.is(expected)
    }

    @Test
    void updateCurrentUserShouldSaveCurrentUserAndReAuthenticate() {
        when(service.userDao.save(currentUser)).thenReturn(currentUser)
        def result = service.updateCurrentUser(true)
        verify(service.userDao).save(currentUser)
        verify(service.userLookupStrategy).reAuthenticateCurrentUser()
        assert result == currentUser
    }
    
    @Test
    void updateCurrentUserShouldSaveCurrentUserWithoutReauthencation() {
        when(service.userDao.save(currentUser)).thenReturn(currentUser)
        def result = service.updateCurrentUser(false)
        verify(service.userDao).save(currentUser)
        verify(service.userLookupStrategy, never()).reAuthenticateCurrentUser()
        assert result == currentUser
    }


}
