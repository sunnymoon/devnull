package org.devnull.orm.util

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageRequest
import org.devnull.util.pagination.Pagination
import org.springframework.data.domain.Sort

/**
 * Adapts DevNull Pagination to spring-data-jpa Pageable to prevent coupling from data layer to web layers.
 */
class JpaPaginationAdapter<T> implements Pageable {

    @Delegate
    PageRequest pageRequest

    Pagination<T> pagination

    JpaPaginationAdapter(Pagination<T> pagination) {
        this.pagination = pagination
        this.pageRequest = new PageRequest(
                pagination.page,
                pagination.max,
                Sort.Direction.fromString(pagination.order),
                pagination.sort
        )
    }
}
