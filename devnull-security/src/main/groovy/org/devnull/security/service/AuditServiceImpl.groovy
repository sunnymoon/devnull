package org.devnull.security.service

import org.devnull.security.audit.AuditPagination
import org.devnull.security.audit.AuditRevision
import org.devnull.security.model.User
import org.hibernate.envers.AuditReaderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import org.hibernate.envers.query.AuditEntity

@Service("auditService")
@Transactional(readOnly = true)
class AuditServiceImpl implements AuditService {

    @Autowired
    EntityManagerFactory entityManagerFactory

    @Autowired
    SecurityService securityService

    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity) {
        return findAllByEntity(entity, new AuditPagination())
    }

    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity, AuditPagination pagination) {
        return findAllByEntity(entity, pagination, null)
    }

    def <T> List<AuditRevision<T>> findAllByEntity(Class<T> entity, AuditPagination pagination, Closure queryEditor) {
        return doWithEntityManager { EntityManager manager ->
            def reader = AuditReaderFactory.get(manager)
            def query = reader.createQuery().forRevisionsOfEntity(entity, false, pagination.selectedDeletedEntities)
                    .addOrder(pagination.orderBy)
                    .setMaxResults(pagination.max)
                    .setFirstResult(pagination.offset)
            pagination.filter.each {  query.add(it)  }
            query.resultList.collect {
                //noinspection GroovyAssignabilityCheck
                new AuditRevision<T>(entity: it[0], revision: it[1], type: it[2])
            }
        }
    }

    Map<String, User> collectUsersFromRevisions(List<AuditRevision> audits) {
        def users = [:]
        audits.collect { it.revision.modifiedBy }.unique().collect {
            users[it] = securityService.findByUserName(it) ?: new User(userName: it, firstName: "Deleted", lastName: "User")
        }
        return users
    }

    def doWithEntityManager = { closure ->
        def manager = entityManagerFactory.createEntityManager()
        try {
            return closure(manager)
        }
        finally {
            manager.close()
        }
    }
}
