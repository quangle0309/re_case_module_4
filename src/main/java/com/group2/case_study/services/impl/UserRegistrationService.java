package com.group2.case_study.services.impl;

import com.group2.case_study.models.Role;
import com.group2.case_study.models.User;
import com.group2.case_study.models.UserRole;
import com.group2.case_study.repositories.IUserRepository;
import com.group2.case_study.repositories.IRoleRepository;
import com.group2.case_study.repositories.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserRoleRepository userRoleRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(User user, String roleName) {
        logger.info("Starting user registration for email: {}", user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);

        if (user.getFullname() == null || user.getFullname().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }



        // Lưu người dùng
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());

        // Lưu role nếu nó chưa tồn tại
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
            logger.info("Role saved with ID: {}", role.getId());
        }

        // Lưu user role
        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        logger.info("UserRole saved for user ID: {}", savedUser.getId());

        return savedUser;
    }
}
