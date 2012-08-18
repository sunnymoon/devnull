package org.devnull.security.service

import org.devnull.security.model.Role
import org.devnull.security.model.User

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
     * Save any changes to the authenticated user and optionally re-authenticate
     *
     * @param reAuthenticate if true, user's security context will be re-authenticated
     */
    User updateCurrentUser(Boolean reAuthenticate)

    /**
     * Lookup an existing role by name
     */
    Role findRoleByName(String name)

    /**
     * Find all users in the system.
     */
    List<User> listUsers()

    /**
     * Find all of the roles in the system.
     */
    List<Role> listRoles()

    User addRoleToUser(Integer roleId, Integer userId)

    /**
     * Remove the given role from the given user's role collection.
     */
    User removeRoleFromUser(Integer roleId, Integer userId)


}