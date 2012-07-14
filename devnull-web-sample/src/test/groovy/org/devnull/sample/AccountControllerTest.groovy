package org.devnull.sample

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
    void profileShouldReturnCorrectModelAndView() {
        def principal = mock(Principal)
        def mv = controller.profile(principal)
        assert mv.viewName == "profile"
        assert mv.model.profile == principal
    }
}
