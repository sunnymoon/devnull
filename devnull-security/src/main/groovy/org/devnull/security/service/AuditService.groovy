package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User
import org.devnull.security.audit.AuditPagination

interface AuditService {
    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity)
    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity, AuditPagination pagination)
    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity, AuditPagination pagination, Closure queryEditor)
    Map<String, User> collectUsersFromRevisions(List<AuditRevision> audits)
}
