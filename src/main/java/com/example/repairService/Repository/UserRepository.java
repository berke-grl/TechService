package com.example.repairService.Repository;

import com.example.repairService.Model.Role;
import com.example.repairService.Model.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public SystemUser getByUserName(String username) {
        String sql = "select * from \"USERS\" where \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);
        return jdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(SystemUser.class));
    }

    public List<GrantedAuthority> getUserRoles(String username) {
        String sql = "select \"role\" from \"USERS\" where \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);

        List<String> list = jdbcTemplate.queryForList(sql, paramMap, String.class);
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : list) {
            roles.add(new Role(role));
        }

        return roles;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public long getCurrentUserId() {
        String username = getCurrentUsername();
        String sql = "Select \"id\" From \"USERS\" Where \"username\" = :USERNAME";
        Map<String, String> param = new HashMap<>();
        param.put("USERNAME", username);

        return jdbcTemplate.queryForObject(sql, param, Long.class);
    }
}
