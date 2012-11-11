package org.devnull.util.pagination

import javax.servlet.http.HttpServletRequest

/**
 * Helps bind http request parameters to pagination fields. See the PARAM constants.
 */
class HttpRequestPagination<T> extends SimplePagination<T> {
    static final String PARAM_MAX = "max"
    static final String PARAM_PAGE = "page"
    static final String PARAM_SORT = "sort"
    static final String PARAM_ORDER = "order"

    HttpRequestPagination(HttpServletRequest request) {
        def max = request.getParameter(PARAM_MAX)
        def page = request.getParameter(PARAM_PAGE)
        def sort = request.getParameter(PARAM_SORT)
        def order = request.getParameter(PARAM_ORDER)

        if (max) this.max = max.toInteger()
        if (page) this.page = page.toInteger()
        if (sort) this.sort = sort
        if (order) this.order = order
    }
}
