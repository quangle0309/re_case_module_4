package com.group2.case_study.services.impl;

import com.group2.case_study.models.User;
import com.group2.case_study.repositories.IUserRepository;
import com.group2.case_study.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User findByName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
