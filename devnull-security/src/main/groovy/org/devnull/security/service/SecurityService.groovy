package org.devnull.security.service

import org.devnull.security.model.User

public interface SecurityService {
    User getCurrentUser()
    User save(User user)
    void register(Long id)
}