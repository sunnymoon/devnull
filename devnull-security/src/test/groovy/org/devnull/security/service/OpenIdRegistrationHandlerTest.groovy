package org.devnull.security.service

import org.devnull.security.converter.AuthenticationConverter
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDAuthenticationToken
import static org.mockito.Mockito.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.devnull.security.dao.UserDao
import org.springframework.security.core.context.SecurityContextHolder
import org.mockito.Matchers
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.devnull.security.dao.RoleDao
import org.mockito.ArgumentCaptor

public class OpenIdRegistrationHandlerTest {
    OpenIdRegistrationHandler handler
    MockHttpServletRequest mockRequest
    MockHttpServletResponse mockResponse

    @Before
    void createHandler() {
        mockRequest = new MockHttpServletRequest()
        mockResponse = new MockHttpServletResponse()
        handler = new OpenIdRegistrationHandler(
                authenticationConverter: mock(AuthenticationConverter),
                authenticationManager: mock(AuthenticationManager),
                userDao: mock(UserDao),
                roleDao: mock(RoleDao)
        )
    }


    @Test
    void onAuthenticationFailureShouldDenyWhenConverterThrowsException() {
        def exception = mock(AuthenticationException)
        when(handler.authenticationConverter.convert(Matchers.any(Authentication))).thenThrow(new InsufficientAuthenticationException("test error"))
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        verify(handler.authenticationConverter).convert(exception.authentication)
        assert mockResponse.status == 401
    }

    @Test
    void onAuthenticationFailureShouldSaveAndRedirectWhenConverterCreatesUser() {
        def mockUser = new User()
        def exception = mock(AuthenticationException)

        when(handler.authenticationConverter.convert(Matchers.any(Authentication))).thenReturn(mockUser)
        when(handler.userDao.save(mockUser)).thenReturn(mockUser)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        verify(handler.userDao).save(mockUser)
        verify(handler.authenticationManager).authenticate(exception.authentication)

        assert !mockUser.registered
        assert mockResponse.status < 400
        assert mockResponse.redirectedUrl == handler.registrationUrl
    }
    
    void onAuthenticationFailureShouldAddDefaultRolesToUser() {
        def mockUser = new User()
        def exception = mock(AuthenticationException)

        when(handler.authenticationConverter.convert(Matchers.any(Authentication))).thenReturn(mockUser)
        when(handler.userDao.save(mockUser)).thenReturn(mockUser)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        handler.defaultRoles.each { roleName ->
            verify(handler.roleDao).findByName(roleName)
            assert mockUser.roles.find { it.name == roleName }
        }
    }


    protected OpenIDAuthenticationToken createToken(OpenIDAuthenticationStatus status, List<OpenIDAttribute> attributes = []) {
        return new OpenIDAuthenticationToken(status, "http://fake.openid.com", "test error", attributes)
    }
}
