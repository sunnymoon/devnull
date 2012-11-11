package org.devnull.orm.util

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageRequest
import org.devnull.util.Pagination
import org.springframework.data.domain.Sort

/**
 * Adapts DevNull Pagination to spring-data-jpa Pageable to prevent coupling from data layer to web layers.
 */
class JpaPaginationAdapter implements Pageable {

    @Delegate
    PageRequest pageRequest

    Pagination pagination

    JpaPaginationAdapter(Pagination pagination) {
        this.pagination = pagination
        this.pageRequest = new PageRequest(
                pagination.page,
                pagination.max,
                Sort.Direction.fromString(pagination.order),
                pagination.sort
        )
    }
}
