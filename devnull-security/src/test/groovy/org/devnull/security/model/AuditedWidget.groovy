package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import org.springframework.data.jpa.domain.AbstractAuditable

import javax.persistence.Entity
import groovy.transform.ToString

@Entity
@EqualsAndHashCode
@ToString(includeNames=true)
class AuditedWidget extends AbstractAuditable<User, Integer> {

  String name

}
