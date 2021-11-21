package org.glsid3.securityservice.sevices;

import org.glsid3.securityservice.dto.AppRoleRequestDto;
import org.glsid3.securityservice.dto.AppRoleResponseDto;
import org.glsid3.securityservice.dto.AppUserRequestDto;
import org.glsid3.securityservice.dto.AppUserResponseDto;
import org.glsid3.securityservice.entities.AppRole;
import org.glsid3.securityservice.entities.AppUser;
import org.glsid3.securityservice.mappers.IRoleMapper;
import org.glsid3.securityservice.mappers.IUserMapper;
import org.glsid3.securityservice.repositories.AppRoleRepository;
import org.glsid3.securityservice.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    private IUserMapper userMapper;
    private IRoleMapper roleMapper;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder, IUserMapper userMapper, IRoleMapper roleMapper) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public AppUserResponseDto addNewUser(AppUserRequestDto appUserRequestDto) {
        String pw=appUserRequestDto.getPassword();
        AppUser appUser=userMapper.appUserRequestDtoToAppUser(appUserRequestDto);
        appUser.setPassword(passwordEncoder.encode(pw));
        appUserRepository.save(appUser);
        return userMapper.appUserToAppUserResponseDto(appUser);
    }

    @Override
    public AppRoleResponseDto addNewRole(AppRoleRequestDto appRoleRequestDto) {
        AppRole appRole=roleMapper.appRoleRequestDtoToAppRole(appRoleRequestDto);
        return roleMapper.appRoleToAppRoleResponseDto(appRoleRepository.save(appRole));
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser=appUserRepository.findAppUserByUserName(userName);
        AppRole appRole=appRoleRepository.findAppRoleByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return appUserRepository.findAppUserByUserName(userName);
    }

    @Override
    public List<AppUserResponseDto> listUsers() {
        List<AppUser> appUsers= appUserRepository.findAll();
        List<AppUserResponseDto> appUserResponseDtos=appUsers.stream().map(appUser ->
                userMapper.appUserToAppUserResponseDto(appUser)).collect(Collectors.toList());
        return appUserResponseDtos;
    }
}
