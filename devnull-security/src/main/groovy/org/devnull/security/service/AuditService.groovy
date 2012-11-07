package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User

interface AuditService {
    public <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity)
    Map<String, User> collectUsersFromRevisions(List<AuditRevision> audits)
}
