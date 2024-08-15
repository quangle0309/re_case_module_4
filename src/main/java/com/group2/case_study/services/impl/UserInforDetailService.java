package com.group2.case_study.services.impl;


import com.group2.case_study.dtos.UserInfoUserDetails;
import com.group2.case_study.models.User;
import com.group2.case_study.models.UserRole;
import com.group2.case_study.repositories.IUserRepository;
import com.group2.case_study.repositories.IUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInforDetailService implements UserDetailsService {
    private final IUserRepository iUserRepository;

    private final IUserRoleRepository iUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<UserRole> userRole = iUserRoleRepository.findAllByUser(user);
        UserInfoUserDetails inforUserDetails = new UserInfoUserDetails(user, userRole);
        return inforUserDetails;
    }
}
