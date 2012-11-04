package org.devnull.security.audit

import groovy.time.TimeCategory
import org.devnull.security.BaseSecurityIntegrationTest
import org.devnull.security.model.AuditedWidget
import org.devnull.security.model.User
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.NotTransactional
import org.devnull.security.dao.AuditedWidgetDao
import org.devnull.security.dao.UserDao

class SecurityAuditorIntegrationTest extends BaseSecurityIntegrationTest {
    @Autowired
    AuditedWidgetDao dao

    @Autowired
    UserDao userDao

    User creator
    AuditedWidget widget

    @Before
    void createWidget() {
        creator = userDao.findOne(1)
        loginAsUser(creator)
        widget = new AuditedWidget(name: "new widget")
        widget = dao.save(widget)
    }


    @Test
    void shouldPopulateAuditFieldsWhenWidgetIsCreated() {
        use(TimeCategory) {
            assert widget.createdBy == creator
            assert widget.createdDate <= new DateTime(new Date())
            assert widget.createdDate >= new DateTime(new Date() - 1.minute)

            assert widget.lastModifiedBy == widget.createdBy
            assert widget.lastModifiedDate == widget.createdDate
        }
    }


}