package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User
import org.hibernate.envers.AuditReaderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManagerFactory

@Service("auditService")
@Transactional(readOnly = true)
class AuditServiceImpl implements AuditService {

    @Autowired
    EntityManagerFactory entityManagerFactory

    @Autowired
    SecurityService securityService


    public <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity) {
        def manager = entityManagerFactory.createEntityManager()
        def audits = []
        try {
            def reader = AuditReaderFactory.get(manager)
            def query = reader.createQuery().forRevisionsOfEntity(entity, false, true)
            audits = query.resultList.collect {
                //noinspection GroovyAssignabilityCheck
                new AuditRevision<T>(entity: it[0], revision: it[1], type: it[2])
            }
        }
        finally {
            manager.close()
        }
        return audits
    }

    Map<String, User> collectUsersFromRevisions(List<AuditRevision> audits) {
        def users = [:]
        audits.collect { it.revision.modifiedBy }.unique().collect {
            users[it] = securityService.findByUserName(it)
        }
        return users
    }
}
