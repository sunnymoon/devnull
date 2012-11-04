package org.devnull.security.audit

import org.devnull.security.model.UserRevision
import org.hibernate.envers.RevisionType

class AuditRevision<T> {
    T entity
    UserRevision revision
    RevisionType type
}
