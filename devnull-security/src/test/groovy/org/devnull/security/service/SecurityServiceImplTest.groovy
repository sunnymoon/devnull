package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.UserDao
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.*

public class SecurityServiceImplTest {
    SecurityServiceImpl service
    User currentUser

    @Before
    void createService() {
        service = new SecurityServiceImpl(userDao: mock(UserDao), userLookupStrategy: mock(UserLookupStrategy))
        currentUser = new User(id: 20314, openId: "http://test.openid.com", firstName: "John", lastName: "Doe", email: "jdoe@test.com")
        when(service.userLookupStrategy.lookupCurrentUser()).thenReturn(currentUser)
    }

    @Test
    void registerShouldLookupUserAndSetRegisteredFlagToTrue() {
        def user = new User(id: 1, registered: false)
        when(service.userDao.findOne(1L)).thenReturn(user)
        service.register(1)
        verify(service.userDao).findOne(1L)
        verify(service.userDao).save(user)
        assert user.registered
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

}
