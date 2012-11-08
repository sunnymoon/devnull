package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User
import org.devnull.security.audit.AuditPagination

interface AuditService {
    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity)
    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity, AuditPagination pagination)
    Map<String, User> collectUsersFromRevisions(List<AuditRevision> audits)
}
