package org.devnull.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.devnull.security.dao.OpenIdUserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("openIdUserDetailsService")
class OpenIdUserDetailsService implements UserDetailsService {
    @Autowired
    OpenIdUserDao userDao

    UserDetails loadUserByUsername(String openIdToken) {
        def user = userDao.findOne(openIdToken)
        if (user) return user
        throw new UsernameNotFoundException("Unable to locate user with open id token :${openIdToken}")
    }
}
