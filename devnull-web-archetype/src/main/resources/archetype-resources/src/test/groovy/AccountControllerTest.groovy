#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}

import java.security.Principal
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.*

class AccountControllerTest {
    AccountController controller

    @Before
    void createController() {
        controller = new AccountController()
    }

    @Test
    void loginShouldReturnCorrectView() {
        assert controller.login() == "login"
    }

    @Test
    void profileShouldReturnCorrectView() {
        assert "profile" == controller.profile()
    }
}
