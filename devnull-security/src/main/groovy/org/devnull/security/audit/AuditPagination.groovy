package org.devnull.security.audit

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.envers.query.AuditEntity
import org.hibernate.envers.query.criteria.AuditCriterion
import org.hibernate.envers.query.order.AuditOrder

@EqualsAndHashCode
@ToString(includeNames = true)
class AuditPagination {
    Integer max = 100
    Integer offset = 0
    AuditOrder orderBy = AuditEntity.revisionNumber().desc()
    Boolean selectedDeletedEntities = true
    List<AuditCriterion> filter = []
}
