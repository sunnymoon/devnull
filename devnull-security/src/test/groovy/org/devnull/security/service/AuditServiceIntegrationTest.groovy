package org.devnull.security.service

import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.dao.AuditedWidgetDao
import org.devnull.security.dao.UserDao
import org.devnull.security.model.AuditedWidget
import org.devnull.security.model.User
import org.hibernate.envers.RevisionType
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
    AuditedWidget widget

    @Before
    void createWidget() {
        auditor = userDao.findOne(1)
        loginAsUser(auditor)
        widget = new AuditedWidget(name: "new widget")
        widget = dao.save(widget)
    }

    /**
     * Test must not be transactional in order for enver's listeners to fire.
     */
    @Test
    @NotTransactional
    void shouldCreateRevisions() {
        def another = new AuditedWidget(name: "another widget")
        dao.save(another)
        another.name = "yet another widget"
        dao.save(another)
        dao.delete(another.id)

        def audits = auditService.findAllByEntity(AuditedWidget)
        assert audits.size() == 4

        assert audits[0].type == RevisionType.ADD
        assert audits[0].entity.name == "new widget"
        assert audits[0].revision.userName == auditor.userName

        assert audits[1].type == RevisionType.ADD
        assert audits[1].entity.name == "another widget"
        assert audits[1].revision.userName == auditor.userName

        assert audits[2].type == RevisionType.MOD
        assert audits[2].entity.name == "yet another widget"
        assert audits[2].revision.userName == auditor.userName

        assert audits[3].type == RevisionType.DEL
        assert audits[3].entity.name == null
        assert audits[3].revision.userName == auditor.userName
    }


}