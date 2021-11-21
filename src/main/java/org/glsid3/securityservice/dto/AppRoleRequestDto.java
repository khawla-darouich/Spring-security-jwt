package org.glsid3.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRoleRequestDto {
    private Long id;
    private String roleName;
}
