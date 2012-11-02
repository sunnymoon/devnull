package org.devnull.security.dao

import org.devnull.security.model.AuditedWidget
import org.springframework.data.repository.PagingAndSortingRepository

interface AuditedWidgetDao extends PagingAndSortingRepository<AuditedWidget, Integer> {

}