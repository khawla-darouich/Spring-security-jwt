package org.glsid3.securityservice.repositories;

import org.glsid3.securityservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUserName(String username);
}
