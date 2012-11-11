package org.devnull.orm.util

import org.devnull.util.Pagination
import org.junit.Test
import org.springframework.data.domain.Pageable

class JpaPaginationAdapterTest {
    @Test
    void shouldConvertFromPaginationToPageable() {
        def pagination = new Pagination(
                sort: "foo", page: 3, max: 10
        )
        def adapter = new JpaPaginationAdapter(pagination)
        assert adapter instanceof Pageable
        assert adapter.sort.getOrderFor("foo").direction.toString() == pagination.order
        assert adapter.pageNumber == 3
        assert adapter.pageSize == 10
        assert adapter.offset == 30
    }
}
