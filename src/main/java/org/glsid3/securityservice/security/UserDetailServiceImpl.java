package org.glsid3.securityservice.security;

import org.glsid3.securityservice.entities.AppUser;
import org.glsid3.securityservice.sevices.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;

    public UserDetailServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByUserName(s);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoles().forEach(appRole -> {
            authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
        });
        return new User(appUser.getUserName(),appUser.getPassword(),authorities);
    }
}
