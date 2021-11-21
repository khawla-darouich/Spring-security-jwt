package org.glsid3.securityservice.mappers;

import org.glsid3.securityservice.dto.AppUserRequestDto;
import org.glsid3.securityservice.dto.AppUserResponseDto;
import org.glsid3.securityservice.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    AppUserResponseDto appUserToAppUserResponseDto(AppUser appUser);
    AppUser appUserRequestDtoToAppUser(AppUserRequestDto appUserRequestDto);
}
