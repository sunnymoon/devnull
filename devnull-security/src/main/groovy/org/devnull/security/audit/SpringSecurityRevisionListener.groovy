package org.devnull.security.audit

import org.hibernate.envers.RevisionListener
import org.devnull.security.model.UserRevision
import org.springframework.security.core.context.SecurityContextHolder
import org.devnull.security.model.User

/**
 * Adds Spring Security username to Hibernate Envers revision metadata
 */
class SpringSecurityRevisionListener implements RevisionListener {
    void newRevision(Object revisionEntity) {
        def userRevision = revisionEntity as UserRevision
        def user = SecurityContextHolder.context?.authentication?.principal as User
        userRevision.userName = user.userName
    }
}
