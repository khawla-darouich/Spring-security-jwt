package org.glsid3.securityservice.sevices;

import org.glsid3.securityservice.dto.AppRoleRequestDto;
import org.glsid3.securityservice.dto.AppRoleResponseDto;
import org.glsid3.securityservice.dto.AppUserRequestDto;
import org.glsid3.securityservice.dto.AppUserResponseDto;
import org.glsid3.securityservice.entities.AppRole;
import org.glsid3.securityservice.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUserResponseDto addNewUser(AppUserRequestDto appUser);
    AppRoleResponseDto addNewRole(AppRoleRequestDto appRole);
    void addRoleToUser(String userName, String roleName);
    AppUser loadUserByUserName(String userName);
    List<AppUserResponseDto> listUsers();
}
