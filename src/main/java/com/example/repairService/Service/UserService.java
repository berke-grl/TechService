package com.example.repairService.Service;

import com.example.repairService.Model.SystemUser;
import com.example.repairService.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser myUser = userRepository.getByUserName(username);


        User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(myUser.getUsername());
        builder.password(myUser.getPassword());
        List<GrantedAuthority> userRoles = userRepository.getUserRoles(myUser.getUsername());
        builder.authorities(userRoles);

        return builder.build();
    }
}
