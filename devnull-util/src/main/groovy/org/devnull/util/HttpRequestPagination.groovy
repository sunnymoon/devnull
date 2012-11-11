package org.devnull.util

import javax.servlet.http.HttpServletRequest

class HttpRequestPagination<T> extends Pagination<T> {
    static final String PARAM_MAX = "max"
    static final String PARAM_OFFSET = "offset"
    static final String PARAM_SORT = "sort"
    static final String PARAM_ORDER = "order"

    HttpRequestPagination(HttpServletRequest request) {
        def max = request.getParameter(PARAM_MAX)
        def offset = request.getParameter(PARAM_OFFSET)
        def sort = request.getParameter(PARAM_SORT)
        def order = request.getParameter(PARAM_ORDER)

        if (max) this.max = max.toInteger()
        if (offset) this.offset = offset.toInteger()
        if (sort) this.sort = sort
        if (order) this.order = order

        if (this.order && this.order != Pagination.ORDER_ASC && this.order != Pagination.ORDER_DESC) {
            throw new IllegalArgumentException("Invalid order parameter ${this.order}. Must be ${Pagination.ORDER_ASC} or ${Pagination.ORDER_DESC}")
        }
    }
}
