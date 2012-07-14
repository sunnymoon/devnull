package org.devnull.sample

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.security.core.context.SecurityContextHolder
import java.security.Principal
import org.springframework.web.servlet.ModelAndView
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User

@Controller
class AccountController {
    @RequestMapping("/login")
    String login() {
        return "login"
    }

    @RequestMapping("/profile")
    String profile() {
        return "profile"
    }
}
