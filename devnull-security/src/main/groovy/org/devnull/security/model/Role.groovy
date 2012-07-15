package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import groovy.transform.ToString

@Entity
@Table(name="SecurityRole")
@EqualsAndHashCode
@ToString(includeFields=true)
class Role implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    String name
}