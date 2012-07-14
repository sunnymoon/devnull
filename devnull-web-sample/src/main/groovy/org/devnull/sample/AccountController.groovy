package org.devnull.sample

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AccountController {
    @RequestMapping("/login")
    String index() {
        return "login"
    }
    
    @RequestMapping("/profile")
    String profile() {
        return "profile"
    }
}
