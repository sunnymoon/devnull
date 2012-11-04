package org.devnull.security.service

import org.devnull.security.audit.AuditRevision

interface AuditService {
    List<AuditRevision<?>> findAllByEntity(Class<?> entity)
}
