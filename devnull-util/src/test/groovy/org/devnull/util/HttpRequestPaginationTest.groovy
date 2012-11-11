package org.devnull.util

import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest

class HttpRequestPaginationTest {

    @Test
    void shouldFindMaxParamFromRequestIfPresent() {
        def request = new MockHttpServletRequest()
        request.setParameter(HttpRequestPagination.PARAM_MAX, "12")
        def pagination = new HttpRequestPagination(request)
        assert pagination.max == 12
    }

    @Test
    void shouldUseDefaultMaxParamIfNotPresent() {
        def pagination = new HttpRequestPagination(new MockHttpServletRequest())
        assert pagination.max == new Pagination().max
    }


    @Test
    void shouldFindOffsetParamFromRequestIfPresent() {
        def request = new MockHttpServletRequest()
        request.setParameter(HttpRequestPagination.PARAM_OFFSET, "2")
        def pagination = new HttpRequestPagination(request)
        assert pagination.offset == 2
    }

    @Test
    void shouldUseDefaultOffsetParamIfNotPresent() {
        def pagination = new HttpRequestPagination(new MockHttpServletRequest())
        assert pagination.offset == new Pagination().offset
    }


    @Test
    void shouldFindSortParamFromRequestIfPresent() {
        def request = new MockHttpServletRequest()
        request.setParameter(HttpRequestPagination.PARAM_SORT, "fieldA")
        def pagination = new HttpRequestPagination(request)
        assert pagination.sort == "fieldA"
    }

    @Test
    void shouldUseDefaultSortParamIfNotPresent() {
        def pagination = new HttpRequestPagination(new MockHttpServletRequest())
        assert pagination.sort == new Pagination().sort
    }


    @Test
    void shouldFindOrderParamFromRequestIfPresent() {
        def request = new MockHttpServletRequest()
        request.setParameter(HttpRequestPagination.PARAM_ORDER, Pagination.ORDER_ASC)
        def pagination = new HttpRequestPagination(request)
        assert pagination.order == Pagination.ORDER_ASC
    }

    @Test
    void shouldUseDefaultOrderParamIfNotPresent() {
        def pagination = new HttpRequestPagination(new MockHttpServletRequest())
        assert pagination.order == new Pagination().order
    }

}
