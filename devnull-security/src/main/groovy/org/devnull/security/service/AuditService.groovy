package org.devnull.security.service

import org.devnull.security.audit.AuditRevision

interface AuditService {
    public <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity)
}
