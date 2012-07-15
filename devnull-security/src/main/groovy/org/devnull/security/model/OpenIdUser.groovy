package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import javax.persistence.Entity
import javax.persistence.Id
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


@EqualsAndHashCode
@ToString(includeFields = true)
@Entity
class OpenIdUser implements Serializable, UserDetails {

    static final long serialVersionUID = 1L;

    @Id
    String id
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
        return id
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
