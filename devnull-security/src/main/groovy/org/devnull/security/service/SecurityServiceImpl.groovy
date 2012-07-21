package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.UserDao
import org.devnull.security.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.devnull.security.dao.RoleDao
import org.springframework.transaction.annotation.Transactional

@Service("securityService")
@Transactional
class SecurityServiceImpl implements SecurityService {

    final def log = LoggerFactory.getLogger(this.class)

    @Autowired
    UserDao userDao

    @Autowired
    RoleDao roleDao

    @Autowired
    UserLookupStrategy userLookupStrategy

    User getCurrentUser() {
        return userLookupStrategy.lookupCurrentUser()
    }

    User findUserByOpenId(String openId) {
        return userDao.findByOpenId(openId)
    }

    void register(Long id) {
        log.info("Registering user {}", id)
        def user = userDao.findOne(id)
        user.registered = true
        userDao.save(user)
        log.info("User {} registration completed", user)
    }

    User createNewUser(User user, List<String> roles) {
        roles.each { role ->
            log.debug("Adding user to role: {}", role)
            user.roles << roleDao.findByName(role)
        }
        userDao.save(user)
    }

    User save(User user) {
        log.info("Saving user: {}", user)
        User secureUser = mergeUser(user)
        return userDao.save(secureUser)
    }

    protected User mergeUser(User user) {
        def secureUser = getCurrentUser()
        secureUser.email = user.email
        secureUser.lastName = user.lastName
        secureUser.firstName = user.firstName
        return secureUser
    }


}
