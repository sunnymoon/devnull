package org.devnull.security.spring

import groovy.util.logging.Slf4j
import org.devnull.security.model.User
import org.devnull.security.service.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper

@Slf4j
class LdapUserContextMapper implements UserDetailsContextMapper {

    Map<String, List<String>> groupToRolesMapping

    @Autowired
    SecurityService securityService

    Map attributesToPropertyNames = [
            email: "mail",
            lastName: "sn",
            firstName: "givenName"
    ]

    LdapUserContextMapper(Map<String, List<String>> groupToRolesMapping) {
        this.groupToRolesMapping = groupToRolesMapping
    }

    UserDetails mapUserFromContext(DirContextOperations ctx, String username,
                                   Collection<? extends GrantedAuthority> authorities) {
        log.info("Mapping LDAP user to custom User")
        // TODO change user object to not depend on openid
        def user = securityService.findUserByOpenId("http://ldap.devnull.org/${username}")
        if (!user) {
            def openId = "http://ldap.devnull.org/${username}"
            def email = ctx.getStringAttribute(attributesToPropertyNames.email)
            def lastName = ctx.getStringAttribute(attributesToPropertyNames.lastName)
            def firstName = ctx.getStringAttribute(attributesToPropertyNames.firstName)
            log.info("Creating new user: openId={}, email={}, firstName={}, lastName={}", openId, email, firstName, lastName)
            user = addNewUser(
                    new User(firstName: firstName, lastName: lastName, email: email, openId: openId),
                    authorities.collect { it.authority }
            )

        }
        return user
    }

    void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        log.warn("LDAP create/update of users is not supported by this implementation")
    }

    protected User addNewUser(User user, List<String> externalAuthorities) {
        def roles = []
        externalAuthorities.each {
            def mappings = groupToRolesMapping[it]
            if (mappings) {
                roles += mappings
            }
        }
        if (!roles) {
            // TODO better exception.. this isn't the intended use of this exception but it's a close fit for now
            throw new DisabledException("User does not belong to any of the configured roles: ${groupToRolesMapping}. LDAP groups: ${externalAuthorities}")
        }
        return securityService.createNewUser(user, roles)
    }


}
