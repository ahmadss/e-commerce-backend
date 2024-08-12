package com.example.ecommerce.service;

import com.example.ecommerce.entities.Role;
import com.example.ecommerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface RoleService {

    public List<String> getAllRoleNames();
}
