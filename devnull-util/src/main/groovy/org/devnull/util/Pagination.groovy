package org.devnull.util

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames=true)
class Pagination<T> implements List<T> {

    static final String ORDER_ASC = "ASC"
    static final String ORDER_DESC = "DESC"

    @Delegate
    List<T> results = []
    Map filter = [:]
    Integer total = 0
    Integer offset = 0
    Integer max = 25
    String sort
    String order = ORDER_ASC
}
