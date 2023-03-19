package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.payload.CreateRoleDto;
import net.javaApp.Ecommerce.payload.CreateRoleResponseDto;

public interface UserService {

    public CreateRoleResponseDto createRole(CreateRoleDto createRoleDto) ;
}
