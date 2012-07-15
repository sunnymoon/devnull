package org.devnull.security.service

import org.devnull.security.dao.OpenIdUserDao
import org.devnull.security.service.OpenIdUserDetailsService
import org.junit.Test
import static org.mockito.Mockito.*
import org.junit.Before
import org.springframework.security.core.userdetails.UserDetailsService
import org.devnull.security.model.OpenIdUser

class OpenIdUserDetailsServiceTest {

    OpenIdUserDetailsService service

    @Before
    void createService() {
        service = new OpenIdUserDetailsService(userDao: mock(OpenIdUserDao))
    }


    @Test
    void loadUserByNameShouldConstructUser() {
        def mockUser = new OpenIdUser(id:'abc')
        when(service.userDao.findOne('abc')).thenReturn(mockUser)

        def user = service.loadUserByUsername('abc')

        verify(service.userDao, times(1)).findOne('abc')
        assert user.username == "abc"
        assert user.password == "********"
        assert user.accountNonExpired
        assert user.accountNonLocked
        assert user.enabled
        def authorities = user.authorities.collect { it.role }
        assert authorities.size() == 1
        assert authorities.first() == "ROLE_USER"
    }
}
