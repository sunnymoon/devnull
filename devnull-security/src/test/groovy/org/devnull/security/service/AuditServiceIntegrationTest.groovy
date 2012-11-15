package org.devnull.security.service

import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.audit.AuditPagination
import org.devnull.security.dao.AuditedWidgetDao
import org.devnull.security.dao.UserDao
import org.devnull.security.model.AuditedWidget
import org.devnull.security.model.User
import org.hibernate.envers.RevisionType
import org.hibernate.envers.query.AuditEntity
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.NotTransactional

class AuditServiceIntegrationTest extends BaseSecurityIntegrationTest {
    @Autowired
    AuditedWidgetDao dao

    @Autowired
    UserDao userDao

    @Autowired
    AuditService auditService

    User auditor

    @Before
    void login() {
        auditor = userDao.findOne(1)
        loginAsUser(auditor)
    }

    /**
     * Test must not be transactional in order for listeners to fire. This test does a lot
     * due to the non-transactional nature of the testing :(
     */
    @Test
    @NotTransactional
    void shouldCreateRevisions() {
        def widget = new AuditedWidget(name: "another widget")
        dao.save(widget)
        widget.name = "yet another widget"
        dao.save(widget)
        dao.delete(widget.id)

        def audits = auditService.findAllByEntity(AuditedWidget)
        assert audits.size() == 3

        assert audits[0].type == RevisionType.DEL
        assert audits[0].entity.name == null
        assert audits[0].revision.modifiedBy == auditor.userName
        assert audits[0].revision.modifiedDate.clearTime() == new Date().clearTime()

        assert audits[1].type == RevisionType.MOD
        assert audits[1].entity.name == "yet another widget"
        assert audits[1].revision.modifiedBy == auditor.userName
        assert audits[1].revision.modifiedDate.clearTime() == new Date().clearTime()

        assert audits[2].type == RevisionType.ADD
        assert audits[2].entity.name == "another widget"
        assert audits[2].revision.modifiedBy == auditor.userName
        assert audits[2].revision.modifiedDate.clearTime() == new Date().clearTime()

        // reverse directions
        audits = auditService.findAllByEntity(AuditedWidget, new AuditPagination(orderBy: AuditEntity.revisionNumber().asc()))
        assert audits.size() == 3
        assert audits[0].type == RevisionType.ADD
        assert audits[1].type == RevisionType.MOD
        assert audits[2].type == RevisionType.DEL

        // apply filters
        audits = auditService.findAllByEntity(AuditedWidget, new AuditPagination(filter: [AuditEntity.property("name").eq("yet another widget")]))
        assert audits.size() == 1
        assert audits[0].type == RevisionType.MOD
        assert audits[0].entity.name == "yet another widget"
        assert audits[0].revision.modifiedBy == auditor.userName
        assert audits[0].revision.modifiedDate.clearTime() == new Date().clearTime()

        // ignore deletes
        audits = auditService.findAllByEntity(AuditedWidget, new AuditPagination(selectedDeletedEntities: false))
        assert audits.size() == 2
        assert audits[0].type == RevisionType.MOD
        assert audits[1].type == RevisionType.ADD
    }


}