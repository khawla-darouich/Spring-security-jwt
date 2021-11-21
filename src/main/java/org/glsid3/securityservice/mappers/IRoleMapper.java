package org.glsid3.securityservice.mappers;

import org.glsid3.securityservice.dto.AppRoleRequestDto;
import org.glsid3.securityservice.dto.AppRoleResponseDto;
import org.glsid3.securityservice.entities.AppRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    AppRoleResponseDto appRoleToAppRoleResponseDto(AppRole appRole);
    AppRole appRoleRequestDtoToAppRole(AppRoleRequestDto appRoleRequestDto);
}
