package org.devnull.security.audit

import org.hibernate.envers.query.AuditEntity
import org.hibernate.envers.query.criteria.AuditCriterion
import org.hibernate.envers.query.order.AuditOrder

class AuditPagination {
    Integer max = 100
    Integer offset = 0
    AuditOrder orderBy = AuditEntity.revisionNumber().desc()
    List<AuditCriterion> filter = []
}
