package org.devnull.security.service

import org.devnull.security.dao.UserDao

import org.junit.Test
import static org.mockito.Mockito.*
import org.junit.Before

import org.devnull.security.model.User
import org.springframework.security.core.userdetails.UsernameNotFoundException

class OpenIdUserDetailsServiceTest {

    OpenIdUserDetailsService service

    @Before
    void createService() {
        service = new OpenIdUserDetailsService(userDao: mock(UserDao))
    }


    @Test
    void loadUserByNameShouldConstructUser() {
        def mockUser = new User(openId:'abc')
        when(service.userDao.findByOpenId('abc')).thenReturn(mockUser)

        def user = service.loadUserByUsername('abc')

        verify(service.userDao, times(1)).findByOpenId('abc')
        assert user.username == "abc"
        assert user.password == "********"
        assert user.accountNonExpired
        assert user.accountNonLocked
        assert user.enabled
        def authorities = user.authorities.collect { it.role }
        assert authorities.size() == 1
        assert authorities.first() == "ROLE_USER"
    }
    
    @Test(expected=UsernameNotFoundException)
    void loadUserByNameShouldErrorIfUserNotFound() {
        when(service.userDao.findByOpenId('abc')).thenReturn(null)
        service.loadUserByUsername('abc')
    }
}
