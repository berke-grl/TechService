package com.example.repairService.Repository;

import com.example.repairService.Model.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProposalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public ProposalRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public List<Proposal> getAllForUser() {
        String sql = "Select * From \"PROPOSAL\" WHERE \"user_id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", userRepository.getCurrentUserId());
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    public Proposal getById(long id) {
        String sql = "Select * From \"PROPOSAL\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    public boolean deleteById(long id) {
        String sql = "Delete From \"PROPOSAL\" Where \"id\" = :ID And \"user_id\" = :USER_ID";
        Map<String, Long> params = new HashMap<>();
        params.put("ID", id);
        params.put("USER_ID", userRepository.getCurrentUserId());

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean save(Proposal proposal) {
        String sql = "Insert Into \"PROPOSAL\"(\"note\", \"price\", \"user_id\", \"status\", \"product_id\") Values(:NOTE, :PRICE, :USER_ID, :STATUS, :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", proposal.getNote());
        params.put("PRICE", proposal.getPrice());
        params.put("USER_ID", userRepository.getCurrentUserId());
        params.put("STATUS", false);
        params.put("PRODUCT_ID", proposal.getProduct_id());

        return jdbcTemplate.update(sql, params) == 1;
    }
}
