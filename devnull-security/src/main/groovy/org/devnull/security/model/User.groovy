package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "SecurityUser")
@EqualsAndHashCode(excludes = "roles")
@ToString(excludes = "roles", includeFields = true)
class User implements Serializable, UserDetails {

    static final def log = LoggerFactory.getLogger(User)
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
    name = "SecurityUserRole",
    joinColumns = @JoinColumn(name = "UserId"),
    inverseJoinColumns = @JoinColumn(name = "RoleId")
    )
    List<Role> roles = []

    String openId
    String email
    String firstName
    String lastName
    Boolean enabled = true

    /**
     * Immutable once set
     * @param id
     */
    void setId(Long id) {
        if (this.id) {
            log.warn("attempt to alter domain id from: {}, to: {}", this.id, id)
            return
        }
        this.id = id
    }

    /**
     * Immutable once set
     * @param id
     */
    void setOpenId(String openId) {
        if (this.openId) {
            log.warn("attempt to alter openId from: {}, to: {}", this.openId, openId)
            return
        }
        this.openId = openId
    }

    Collection<GrantedAuthority> getAuthorities() {
        // TODO hardcoded: replace this with ManyToMany Role relationship
        return [new SimpleGrantedAuthority("ROLE_USER")] as Set
    }

    String getPassword() {
        return "********"
    }

    String getUsername() {
        return openId
    }

    boolean isAccountNonExpired() {
        return true
    }

    boolean isAccountNonLocked() {
        return true
    }

    boolean isCredentialsNonExpired() {
        return true
    }

    boolean isEnabled() {
        return enabled
    }

    void addToRoles(Role role) {
        if (!roles.contains(role)) {
            roles.add(role)
        }
    }

}
