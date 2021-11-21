package org.glsid3.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.glsid3.securityservice.entities.AppRole;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestDto {
    private Long id;
    private String userName;
    private String password;
    private Collection<AppRole> appRoles=new ArrayList<>();
}
