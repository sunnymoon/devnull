package org.devnull.security.service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.devnull.security.model.User
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Service

@Service("openIdRegistrationHandler")
class OpenIdRegistrationHandler extends SimpleUrlAuthenticationFailureHandler {

    final def log = LoggerFactory.getLogger(this.class)
    static final String SESSION_OPENID_TEMP_USER = "OPENID_TEMP_USER"
    String registrationUrl = "/register"

    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if (e.authentication instanceof OpenIDAuthenticationToken) {
            def user = createTempUser(e.authentication as OpenIDAuthenticationToken)
            redirectToRegistration(request, response, user)
            return
        }
        super.onAuthenticationFailure(request, response, e)
    }

    protected void redirectToRegistration(HttpServletRequest request, HttpServletResponse response, User user) {
        def strategy = new DefaultRedirectStrategy()
        request.getSession(true).setAttribute(SESSION_OPENID_TEMP_USER, user)
        strategy.sendRedirect(request, response, registrationUrl)
    }

    protected User createTempUser(OpenIDAuthenticationToken token) {
        if (token.status == OpenIDAuthenticationStatus.SUCCESS) {
            def email = token.attributes.find { it.name == "email" }
            return new User(openId: token.identityUrl, email: email?.values?.first())
        }
        else {
            log.warn("Invalid OpenID auth status: {}, message: {}", token.status, token.message)
            throw new InsufficientAuthenticationException("Unsuccessfull OpenID authenitcation status: ${token.status}, message: ${token.message}")
        }
    }
}