package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.RoleDao
import org.devnull.security.dao.UserDao

import org.devnull.security.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

    @Transactional(readOnly = true)
    User findUserByOpenId(String openId) {
        return userDao.findByOpenId(openId)
    }

    User createNewUser(User user, List<String> roles) {
        roles.each { role ->
            log.debug("Adding user to role: {}", role)
            user.roles << roleDao.findByName(role)
        }
        userDao.save(user)
    }

    User updateCurrentUser(User user) {
        log.info("Saving user: {}", user)
        User secureUser = mergeWithCurrentUser(user)
        return userDao.save(secureUser)
    }

    void removeRoles(List<String> roles) {
        def user = currentUser
        log.info("Removing roles: {} from user: {}", roles, user)
        user.roles.removeAll { roles.contains(it.name)  }
        userDao.save(user)
    }


    void addRoles(List<String> roles) {
        def user = currentUser
        log.info("Adding roles: {} to user: {}", roles, user)
        roles.each { role ->
            user.addToRoles(roleDao.findByName(role))
        }
        userDao.save(user)
    }

    protected User mergeWithCurrentUser(User user) {
        def secureUser = getCurrentUser()
        secureUser.email = user.email
        secureUser.lastName = user.lastName
        secureUser.firstName = user.firstName
        return secureUser
    }


}
