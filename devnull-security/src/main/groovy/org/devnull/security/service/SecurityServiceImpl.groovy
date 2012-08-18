package org.devnull.security.service

import org.devnull.security.config.UserLookupStrategy
import org.devnull.security.dao.RoleDao
import org.devnull.security.dao.UserDao
import org.devnull.security.model.Role
import org.devnull.security.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("securityService")
@Transactional(readOnly=true)
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

    @Transactional(readOnly=false)
    User createNewUser(User user, List<String> roles) {
        roles.each { role ->
            log.debug("Adding user to role: {}", role)
            user.roles << roleDao.findByName(role)
        }
        userDao.save(user)
    }

    @Transactional(readOnly=false)
    User updateCurrentUser(Boolean reAuthenticate) {
        def user = currentUser
        log.info("Saving user: {}", user)
        userDao.save(user)
        if (reAuthenticate) {
            userLookupStrategy.reAuthenticateCurrentUser()
        }
        return currentUser
    }

    Role findRoleByName(String name) {
        return roleDao.findByName(name)
    }

    List<User> listUsers() {
        return userDao.findAll(new Sort("lastName")) as List
    }

    @Transactional(readOnly=false)
    void removeRoleFromUser(Integer roleId, Integer userId) {
        def user = userDao.findOne(userId)
        user.roles.removeAll { it.id == roleId }
        userDao.save(user)
    }

    List<Role> listRoles() {
        return roleDao.findAll(new Sort("description")) as List
    }
}
