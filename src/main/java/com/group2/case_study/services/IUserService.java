package com.group2.case_study.services;

import com.group2.case_study.models.User;

public interface IUserService {
    User findByName(String userName);

    User findUserByUsername(String username);
}
