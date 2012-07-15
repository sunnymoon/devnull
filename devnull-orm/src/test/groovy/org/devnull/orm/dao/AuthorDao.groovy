package org.devnull.orm.dao

import org.devnull.orm.model.Author
import org.springframework.data.repository.PagingAndSortingRepository

interface AuthorDao extends PagingAndSortingRepository<Author, Long> {
    List<Author> findByLastName(String lastName);
}
