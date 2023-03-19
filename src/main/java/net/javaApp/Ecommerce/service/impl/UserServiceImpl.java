package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.model.Role;
import net.javaApp.Ecommerce.payload.CreateRoleDto;
import net.javaApp.Ecommerce.payload.CreateRoleResponseDto;
import net.javaApp.Ecommerce.repository.RoleRepository;
import net.javaApp.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepository roleRepository ;

    @Override
    public CreateRoleResponseDto createRole(CreateRoleDto createRoleDto) {
        Role savedRole = roleRepository.save(new Role(createRoleDto.getRole())) ;
        CreateRoleResponseDto savedRoleDto = new CreateRoleResponseDto("Role created successfully", savedRole.getName());
        return savedRoleDto ;
    }
}
