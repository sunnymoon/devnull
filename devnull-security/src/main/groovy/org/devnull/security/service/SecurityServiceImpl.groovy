package org.devnull.security.service

import org.devnull.security.model.User
import org.springframework.stereotype.Service
import org.devnull.security.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired

@Service("securityService")
class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserDao userDao

    User registerNewOpenIdUser(String openIdToken, User user) {
        user.openId = openIdToken
        return userDao.save(user)
    }

}
