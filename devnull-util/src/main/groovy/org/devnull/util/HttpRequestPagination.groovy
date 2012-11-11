package org.devnull.util

import javax.servlet.http.HttpServletRequest

/**
 * Helps bind http request parameters to pagination fields. See the PARAM constants.
 */
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
    }
}
