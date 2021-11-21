package org.glsid3.securityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.glsid3.securityservice.entities.AppRole;

import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseDto {
    private Long id;
    private String userName;
    private Collection<AppRole> appRoles=new ArrayList<>();

}
