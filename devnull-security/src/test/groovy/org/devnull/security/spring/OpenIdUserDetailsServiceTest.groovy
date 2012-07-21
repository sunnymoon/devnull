package org.devnull.security.spring

import org.devnull.security.model.User
import org.devnull.security.service.SecurityService
import org.junit.Before
import org.junit.Test
import org.springframework.security.core.userdetails.UsernameNotFoundException
import static org.mockito.Mockito.*

class OpenIdUserDetailsServiceTest {

    OpenIdUserDetailsService service

    @Before
    void createService() {
        service = new OpenIdUserDetailsService(securityService: mock(SecurityService))
    }


    @Test
    void loadUserByNameShouldConstructUser() {
        def mockUser = new User(openId: 'abc')
        when(service.securityService.findUserByOpenId('abc')).thenReturn(mockUser)
        def user = service.loadUserByUsername('abc')
        verify(service.securityService).findUserByOpenId('abc')
        assert user.is(mockUser)
    }

    @Test(expected = UsernameNotFoundException)
    void loadUserByNameShouldErrorIfUserNotFound() {
        when(service.securityService.findUserByOpenId('abc')).thenReturn(null)
        service.loadUserByUsername('abc')
    }
}
