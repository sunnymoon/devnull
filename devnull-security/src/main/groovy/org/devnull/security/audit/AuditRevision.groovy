package org.devnull.security.audit

import org.devnull.security.model.UserRevision
import org.hibernate.envers.RevisionType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Container for hibernate envers revision data
 */
@EqualsAndHashCode
@ToString(includeNames=true)
class AuditRevision<T> {
    T entity
    UserRevision revision
    RevisionType type
}
