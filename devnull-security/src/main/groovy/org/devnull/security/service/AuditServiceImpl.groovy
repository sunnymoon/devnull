package org.devnull.security.service

import org.devnull.security.audit.AuditRevision
import org.hibernate.envers.AuditReaderFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.persistence.EntityManagerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("auditService")
@Transactional(readOnly=true)
class AuditServiceImpl implements AuditService {

    @Autowired
    EntityManagerFactory entityManagerFactory

    List<AuditRevision<?>> findAllByEntity(Class<?> entity) {
        def manager = entityManagerFactory.createEntityManager()
        def audits = []
        try {
            def reader = AuditReaderFactory.get(manager)
            def query = reader.createQuery().forRevisionsOfEntity(entity, false, true)
            audits = query.resultList.collect {
                //noinspection GroovyAssignabilityCheck
                new AuditRevision<?>(entity:it[0], revision: it[1], type: it[2])
            }
            //            audits = reader.getRevisions(entity, id).collect { rev ->
            //                reader.find(entity, id, rev)
            //            }
        }
        finally {
            manager.close()
        }
        return audits
    }
}
