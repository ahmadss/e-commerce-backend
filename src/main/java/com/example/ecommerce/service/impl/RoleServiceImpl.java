package com.example.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entities.Role;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
    private RoleRepository roleRepository;

    public List<String> getAllRoleNames() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

}
