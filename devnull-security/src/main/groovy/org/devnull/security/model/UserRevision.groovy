package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.devnull.security.audit.SpringSecurityRevisionListener
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@RevisionEntity(SpringSecurityRevisionListener)
@Table(name = "SecurityUserRevision")
@EqualsAndHashCode
@ToString(includeNames = true)
class UserRevision {

    @Id
    @GeneratedValue
    @RevisionNumber
    Integer id

    @RevisionTimestamp
    Long timestamp

    /**
     Loosely coupled to user in order to preserve history in case the user is deleted
     */
    String userName
}
