package org.devnull.util

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames=true)
/**
 * Utility class used for pagination of result sets
 */
class Pagination<T> implements List<T> {

    static final String ORDER_ASC = "ASC"
    static final String ORDER_DESC = "DESC"

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
     * Current page or offset within the total results.
     *
     * Default = {@value}
     */
    Integer offset = 0

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
