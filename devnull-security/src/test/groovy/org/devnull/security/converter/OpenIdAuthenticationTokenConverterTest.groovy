package org.devnull.security.converter

import org.junit.Test
import org.springframework.security.core.AuthenticationException
import org.junit.Before
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDAttribute;

public class OpenIdAuthenticationTokenConverterTest {
    OpenIdAuthenticationTokenConverter converter

    @Before
    void createConverter() {
        converter = new OpenIdAuthenticationTokenConverter()
    }

    @Test(expected=AuthenticationException)
    void convertShouldRejectNonOpenIdAuthentication() {
        converter.convert(new UsernamePasswordAuthenticationToken("testUser", "fakepassword"))
    }
    
    @Test(expected=AuthenticationException)
    void convertShouldRejectNonSuccessfulTokens() {
        converter.convert(createToken(OpenIDAuthenticationStatus.ERROR))
    }

    @Test
    void convertShouldCreateUserFromValidToken() {
        def attributes = [new OpenIDAttribute("email", "http://schema.openid.net/contact/email", ["test@foo.com"])]
        def token = createToken(OpenIDAuthenticationStatus.SUCCESS, attributes)
        def user = converter.convert(token)
        assert user
        assert user.openId == "http://fake.openid.com"
        assert user.email == "test@foo.com"
    }


    protected OpenIDAuthenticationToken createToken(OpenIDAuthenticationStatus status, List<OpenIDAttribute> attributes = []) {
        return new OpenIDAuthenticationToken(status, "http://fake.openid.com", "test error", attributes)
    }
}
