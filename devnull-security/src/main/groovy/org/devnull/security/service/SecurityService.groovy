package org.devnull.security.service

import org.devnull.security.model.User
import org.devnull.security.model.Role

public interface SecurityService {
    /**
     * Locates the current logged in user.
     * @return
     */
    User getCurrentUser()

    /**
     * Find a user with the matching openid token
     *
     * @param openId
     * @return persisted version of the user
     */
    User findUserByOpenId(String openId)

    /**
     * Create a new user with the given roles
     * @param user transient user
     * @param roles starting roles for the user
     * @return persisted version of the user
     */
    User createNewUser(User user, List<String> roles)

    /**
     * Securely update the  the currently logged in user. Excludes openId, id, etc.
     *
     * @param user user object containing the values to be changed
     * @return updated user
     */
    User updateCurrentUser(User user)

    /**
     * Remove the roles from the currently logged in user
     *
     * @param roles which roles you'd like removed.
     */
    void removeRoles(List<String> roles)

    /**
     * Add the roles to the currently logged in user
     *
     * @param roles which roles you'd like added
     */
    void addRoles(List<String> roles)

}