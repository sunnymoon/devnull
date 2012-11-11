package org.devnull.util.pagination

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


/**
 * Utility class used for pagination of result sets
 */
interface Pagination<T> extends List<T> {

    static final String ORDER_ASC = "ASC"
    static final String ORDER_DESC = "DESC"

    /**
     * Contains a subset of the requested rows. Delegate for List.
     */
    List<T> getResults()

    /**
     * Key value pairs used to limit reset set.
     */
    Map getFilter()

    /**
     * Total results available regardless of current results size.
     */
    Integer getTotal()

    /**
     * Current page (slice) of the total results.
     *
     * Default = {@value}
     */
    Integer getPage()

    /**
     * The max number of responses that can be fetched per request.
     */
    Integer getMax()

    /**
     * The field name to sort on
     */
    String getSort()

    /**
     * The direction of the sort.
     *
     * Default= {@value}
     */
    String order = ORDER_ASC
}
