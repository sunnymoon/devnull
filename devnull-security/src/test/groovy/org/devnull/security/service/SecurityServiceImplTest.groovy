package org.devnull.security.service

import org.devnull.security.dao.UserDao
import org.junit.Before
import static org.mockito.Mockito.*
import org.devnull.security.model.User
import org.junit.Test
import org.mockito.ArgumentCaptor

public class SecurityServiceImplTest {
    SecurityServiceImpl service

    @Before
    void createService() {
        service = new SecurityServiceImpl(userDao: mock(UserDao))
    }

    @Test
    void registerNewOpenIdUserShouldSaveUserWithCorrectOpenId() {
        def user = new User(openId: "http://hacked.openid.com", firstName: "Black", lastName: "Hatter")
        service.registerNewOpenIdUser("http://good.openid.com", user)
        def argument = ArgumentCaptor.forClass(User);
        verify(service.userDao).save(argument.capture());
        assert argument.value.openId == "http://good.openid.com"
    }

    @Test
    void registerNewOpenIdUserShouldReturnResultsFromDao() {
        def openId = "http://good.openid.com"
        def user = new User(firstName: "John", lastName: "Doe")
        def mockReturnUser = mock(User)
        when(service.registerNewOpenIdUser(openId, user)).thenReturn(mockReturnUser)
        assert mockReturnUser == service.registerNewOpenIdUser(openId, user)
        verify(service.userDao, times(1)).save(user)
    }
}
