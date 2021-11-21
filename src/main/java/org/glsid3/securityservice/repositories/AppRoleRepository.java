package org.glsid3.securityservice.repositories;

import org.glsid3.securityservice.entities.AppRole;
import org.glsid3.securityservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppRoleRepository extends JpaRepository<AppRole ,Long> {
    AppRole findAppRoleByRoleName(String roleName);
}
