package com.inventory.ims.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String roleName;
    private Boolean isActive;
}