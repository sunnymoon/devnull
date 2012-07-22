package org.devnull.security.spring

import org.devnull.security.converter.AuthenticationConverter
import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDAuthenticationToken
import static org.mockito.Mockito.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.devnull.security.dao.UserDao

import org.mockito.Matchers
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.devnull.security.dao.RoleDao

import org.devnull.security.spring.OpenIdRegistrationHandler
import org.devnull.security.service.SecurityService

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
                securityService: mock(SecurityService)
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
        when(handler.securityService.createNewUser(mockUser, handler.defaultRoles)).thenReturn(mockUser)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        verify(handler.securityService).createNewUser(mockUser,  handler.defaultRoles)
        verify(handler.authenticationManager).authenticate(exception.authentication)

        assert mockResponse.status < 400
        assert mockResponse.redirectedUrl == handler.registrationUrl
    }
    



    protected OpenIDAuthenticationToken createToken(OpenIDAuthenticationStatus status, List<OpenIDAttribute> attributes = []) {
        return new OpenIDAuthenticationToken(status, "http://fake.openid.com", "test error", attributes)
    }
}
