package org.devnull.util.pagination.adapter

import org.devnull.util.pagination.Pagination
import org.devnull.util.pagination.Sort
import org.displaytag.pagination.PaginatedList
import org.displaytag.properties.SortOrderEnum
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DisplayTagPaginatedListAdapterTest {
    PaginatedList tagList
    Pagination pagination

    @Before
    void createList() {
        pagination = mock(Pagination)
        tagList = new DisplayTagPaginatedListAdapter(pagination)
    }

    @Test
    void shouldContainCorrectListContents() {
        when(pagination.results).thenReturn([1, 2, 3])
        assert tagList.list == [1, 2, 3]

    }

    @Test
    void shouldHaveCorrectFullListSize() {
        when(pagination.total).thenReturn(10L)
        assert tagList.fullListSize == 10
    }

    @Test
    void shouldUseMaxIntegerSizeWhenTotalsAreLargerThanCapacity() {
        when(pagination.total).thenReturn(Integer.MAX_VALUE + 17L)
        assert tagList.fullListSize == Integer.MAX_VALUE
    }

    @Test
    void shouldHaveCorrectObjectsPerPage() {
        when(pagination.max).thenReturn(5)
        assert tagList.objectsPerPage == 5
    }

    @Test
    void shouldHaveCorrectPageNumber() {
        when(pagination.page).thenReturn(2)
        assert tagList.pageNumber == 2
    }

    @Test
    void shouldHaveNoSortCriterionIfNotContainedInPagination() {
        assert tagList.sortCriterion == null
    }

    @Test
    void shouldUseFirstSortObjectForCriterionWhenMultipleSortsExistInPagination() {
        def sorts = [new Sort(field: "name"), new Sort(field: "count", direction: Sort.DESC)]
        when(pagination.sorts).thenReturn(sorts)
        assert tagList.sortCriterion == "name"
        assert tagList.sortDirection == SortOrderEnum.ASCENDING
    }

}
