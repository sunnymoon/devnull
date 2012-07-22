package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.UserDao
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.*
import javax.security.sasl.AuthenticationException
import org.devnull.security.model.Role
import org.devnull.security.dao.RoleDao

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
        currentUser.roles = [new Role(id:1, name:"a"), new Role(id:2, name: "b"), new Role(id:3, name: "c")]
        when(service.userLookupStrategy.lookupCurrentUser()).thenReturn(currentUser)
    }

    @Test
       void createNewUserShouldAddRolesToUserAndSave() {
        def mockUser = new User()
        def roles = ["a", "b"]

        when(service.userDao.save(mockUser)).thenReturn(mockUser)
        when(service.roleDao.findByName("a")).thenReturn(new Role(id:1, name:"a"))
        when(service.roleDao.findByName("b")).thenReturn(new Role(id:1, name:"b"))
        def result = service.createNewUser(mockUser, roles)
        assert result
        roles.each { roleName ->
            verify(service.roleDao).findByName(roleName)
            assert result.roles.find { it.name == roleName }
        }
    }

    @Test
    void saveShouldNotUpdateSecuredProperties() {
        def formUser = new User(id: 1, openId: "http://hacked.openid.com", firstName: "Black", lastName: "Hatter", email: "hax@you.com")
        def result = service.mergeUser(formUser)
        verify(service.userLookupStrategy).lookupCurrentUser()

        // users can't change identifiers
        assert result.id == 20314
        assert result.openId == "http://test.openid.com"

        // free to change their own fields
        assert result.firstName == "Black"
        assert result.lastName == "Hatter"
        assert result.email == "hax@you.com"
    }

    @Test
    void removeRolesShouldUpdateCurrentLoggedInUserAndSaveChanges() {
        assert currentUser.roles.size() == 3
        service.removeRoles(["a", "c"])
        assert currentUser.roles.size() == 1
        assert currentUser.roles.first() == new Role(id:2, name:"b")
        verify(service.userDao).save(currentUser)
    }

    @Test
    void addRolesShouldUpdateCurrentLoggedInUserAndSaveChanges() {
        def rolesToAdd = [new Role(id:1, name:"a"), new Role(id:4, name:"d"), new Role(id:5, name:"e")]

        rolesToAdd.each {
            when(service.roleDao.findByName(it.name)).thenReturn(it)
        }

        assert currentUser.roles.size() == 3
        service.addRoles(rolesToAdd.collect {it.name})
        assert currentUser.roles.size() == 5
        verify(service.userDao).save(currentUser)
        rolesToAdd.each {
            verify(service.roleDao).findByName(it.name)
        }
    }

}
