package org.devnull.util.pagination

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Utility class used for pagination of result sets
 */

@EqualsAndHashCode
@ToString(includeNames=true)
class SimplePagination<T> implements Pagination<T> {

    /**
     * Contains requested response objects. Delegate for List.
     *
     * Default = {@value}
     */
    @Delegate
    List<T> results = []

    /**
     * Key value pairs used to limit reset set.
     *
     * Default = {@value}
     */
    Map filter = [:]

    /**
     * Total results available regardless of current results size.
     *
     * Default = {@value}
     */
    Integer total = 0

    /**
     * Current page
     *
     * Default = {@value}
     */
    Integer page = 0

    /**
     * The max number of responses that can be retained in this.results.
     *
     * Default = {@value}
     */
    Integer max = 25

    /**
     * The field name to sort on
     */
    String sort

    /**
     * The direction of the sort.
     *
     * Default= {@value}
     */
    String order = ORDER_ASC
}
