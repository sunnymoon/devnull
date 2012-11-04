package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.jpa.domain.AbstractAuditable

import javax.persistence.Entity

@Entity
@EqualsAndHashCode
@ToString(includeNames = true)
class AuditedWidget extends AbstractAuditable<User, Integer> {

    String name

}
