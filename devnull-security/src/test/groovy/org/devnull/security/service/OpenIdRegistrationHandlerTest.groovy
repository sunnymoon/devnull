package org.devnull.security.service

import org.devnull.security.model.User
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.openid.OpenIDAttribute

public class OpenIdRegistrationHandlerTest {
    OpenIdRegistrationHandler handler

    @Before
    void createHandler() {
        handler = new OpenIdRegistrationHandler()
    }

    @Test
    void onAuthenticationFailureShouldOnlyAcceptExceptionsWithOpenIdTokens() {
        def mockRequest = new MockHttpServletRequest()
        def mockResponse = new MockHttpServletResponse()
        def exception = new UsernameNotFoundException("Test error")
        exception.authentication = new UsernamePasswordAuthenticationToken("foo", "bar")
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        assert mockResponse.status == 401
    }

    @Test(expected = InsufficientAuthenticationException)
    void onAuthenticationFailureShouldRejectInvalidOpenIdTokens() {
        def mockRequest = new MockHttpServletRequest()
        def mockResponse = new MockHttpServletResponse()
        def exception = new UsernameNotFoundException("Test error")
        exception.authentication = createToken(OpenIDAuthenticationStatus.FAILURE)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
    }

    @Test
    void onAuthenticationFailureShouldAcceptValidOpenIdTokens() {
        def mockRequest = new MockHttpServletRequest()
        def mockResponse = new MockHttpServletResponse()
        def exception = new UsernameNotFoundException("Test error")
        exception.authentication = createToken(OpenIDAuthenticationStatus.SUCCESS)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        assert mockResponse.status < 400
    }

    @Test
    void onAuthenticationFailureShouldRedirectToRegisterUrl() {
        def mockRequest = new MockHttpServletRequest()
        def mockResponse = new MockHttpServletResponse()
        def exception = new UsernameNotFoundException("Test error")
        exception.authentication = createToken(OpenIDAuthenticationStatus.SUCCESS)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        assert mockResponse.redirectedUrl == handler.registrationUrl
    }


    @Test
    void onAuthenticationFailureShouldCreateTempUserInSession() {
        def mockRequest = new MockHttpServletRequest()
        def mockResponse = new MockHttpServletResponse()
        def exception = new UsernameNotFoundException("Test error")
        exception.authentication = createToken(OpenIDAuthenticationStatus.SUCCESS)
        handler.onAuthenticationFailure(mockRequest, mockResponse, exception)
        def user = mockRequest.session.getAttribute(OpenIdRegistrationHandler.SESSION_OPENID_TEMP_USER) as User
        assert user.openId == "http://fake.openid.com"
    }

    @Test
    void createTempUserShouldParseOpenIdAttributes() {
        def attributes = [new OpenIDAttribute("email", "http://schema.openid.net/contact/email", ["test@foo.com"])]
        def token = createToken(OpenIDAuthenticationStatus.SUCCESS, attributes)
        def user = handler.createTempUser(token)
        assert user.email == "test@foo.com"
    }

    protected OpenIDAuthenticationToken createToken(OpenIDAuthenticationStatus status, List<OpenIDAttribute> attributes = []) {
        return new OpenIDAuthenticationToken(status, "http://fake.openid.com", "test error", attributes)
    }
}
