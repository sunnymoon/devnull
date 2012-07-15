package org.devnull.orm.dao

import org.devnull.orm.model.Book
import org.springframework.data.repository.CrudRepository

interface BookDao extends CrudRepository<Book, Long> {
}
