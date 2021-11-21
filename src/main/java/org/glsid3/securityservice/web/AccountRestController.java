package org.glsid3.securityservice.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.glsid3.securityservice.dto.AppRoleRequestDto;
import org.glsid3.securityservice.dto.AppRoleResponseDto;
import org.glsid3.securityservice.dto.AppUserRequestDto;
import org.glsid3.securityservice.dto.AppUserResponseDto;
import org.glsid3.securityservice.entities.AppUser;
import org.glsid3.securityservice.security.JWTUTIL;
import org.glsid3.securityservice.sevices.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUserResponseDto> appUsers()
    {
        return accountService.listUsers();
    }

    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUserResponseDto addAppUser(@RequestBody AppUserRequestDto appUserRequestDto)
    {
        return accountService.addNewUser(appUserRequestDto);
    }

    @PostMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRoleResponseDto addAppRole(@RequestBody AppRoleRequestDto appRoleRequestDto)
    {
        return accountService.addNewRole(appRoleRequestDto);
    }

    @PostMapping(path = "/addRoleToUser")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody UserRoleForm userRoleForm)
    {
        accountService.addRoleToUser(userRoleForm.getUserName(),userRoleForm.getRoleName());
    }

    @GetMapping("profile")
    public AppUser currentUser(Principal principal){
        return accountService.loadUserByUserName(principal.getName());
    }

    @GetMapping(path = "/refreshToken")
    public void refrehToken(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
         String authToken=request.getHeader(JWTUTIL.AUTHORIZATION_HEADER);
         if(authToken!=null && authToken.startsWith(JWTUTIL.AUTHORIZATION_PREFIX)){
             try {
                 String refreshToken=authToken.substring(JWTUTIL.AUTHORIZATION_PREFIX.length());
                 Algorithm algorithm=Algorithm.HMAC256(JWTUTIL.SECRET);
                 JWTVerifier verifier= JWT.require(algorithm).build();
                 DecodedJWT decodedJWT=verifier.verify(refreshToken);
                 String username=decodedJWT.getSubject();
                 AppUser appUser=accountService.loadUserByUserName(username);
                 String jwtAccessToken= JWT.create()
                         .withSubject(appUser.getUserName())
                         .withExpiresAt(new Date(System.currentTimeMillis()+JWTUTIL.EXPIRE_ACCESS_TOKEN))
                         .withIssuer(request.getRequestURL().toString())
                         .withClaim("roles",appUser.getAppRoles().stream().map(
                                 role->role.getRoleName()
                         ).collect(Collectors.toList()))
                         .sign(algorithm);
                 Map<String,String> idToken=new HashMap<>();
                 idToken.put("access-token",jwtAccessToken);
                 idToken.put("refresh-token",refreshToken);
                 response.setContentType("application/json");
                 new ObjectMapper().writeValue(response.getOutputStream(),idToken);
             } catch (Exception e) {
                 throw e;
             }
         }else{
             throw new RuntimeException("Valid Refresh Token required !!");
         }
    }
}

@Data
class UserRoleForm{
    String userName;
    String roleName;
}