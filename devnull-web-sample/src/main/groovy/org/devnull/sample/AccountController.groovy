package org.devnull.sample

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.security.core.context.SecurityContextHolder
import java.security.Principal
import org.springframework.web.servlet.ModelAndView

@Controller
class AccountController {
    @RequestMapping("/login")
    String index() {
        return "login"
    }
    
    @RequestMapping("/profile")
    ModelAndView profile(Principal principal) {
        return new ModelAndView("profile", [profile:principal])
    }
}
