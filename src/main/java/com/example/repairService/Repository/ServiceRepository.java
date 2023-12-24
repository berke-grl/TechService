package com.example.repairService.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ServiceRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ServiceRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getServiceDurationById(long id) {
        String sql = "SELECT \"duration\" FROM \"SERVICE\" WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }
}
