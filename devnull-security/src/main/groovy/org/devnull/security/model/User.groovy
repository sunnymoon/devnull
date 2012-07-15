package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue

@Entity
@Table(name="SecurityUser")
@EqualsAndHashCode
@ToString(includeFields = true)
class User implements Serializable, UserDetails {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String openId
    String email
    String firstName
    String lastName

    Boolean enabled = true

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


}
