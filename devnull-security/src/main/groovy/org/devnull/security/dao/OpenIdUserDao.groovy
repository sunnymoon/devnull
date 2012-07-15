package org.devnull.security.dao

import org.springframework.data.repository.PagingAndSortingRepository
import org.devnull.security.model.OpenIdUser

public interface OpenIdUserDao extends PagingAndSortingRepository<OpenIdUser, String> {

}