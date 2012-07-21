package org.devnull.security.service

import org.devnull.security.model.User

public interface SecurityService {
    /**
     * Locates the current logged in user.
     * @return
     */
    User getCurrentUser()
    User findUserByOpenId(String openId)
    User createNewUser(User user, List<String> roles)
    User save(User user)
    void register(Long id)
}