package org.devnull.orm.model

import groovy.transform.EqualsAndHashCode
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@EqualsAndHashCode(excludes = 'books')
class Author implements Serializable {

    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    String firstName
    String lastName

    @OneToMany(mappedBy = 'author', cascade = CascadeType.ALL, orphanRemoval = true)
    List<Book> books = []

    String toString() {
        return "${firstName} ${lastName}"
    }
}
