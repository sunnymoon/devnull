package org.devnull.security.audit

import org.devnull.security.model.UserRevision
import org.hibernate.envers.RevisionType

/**
 * Container for hibernate envers revision data
 */
class AuditRevision<T> {
    T entity
    UserRevision revision
    RevisionType type
}
