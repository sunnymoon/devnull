package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table(name = "SecurityRole")
@EqualsAndHashCode
@ToString(includeNames = true)
class Role implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Pattern(regexp="^ROLE_.*")
    String name

    String description

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
    name = "SecurityUserRole",
    joinColumns = @JoinColumn(name = "RoleId"),
    inverseJoinColumns = @JoinColumn(name = "UserId")
    )
    List<User> users = []
}