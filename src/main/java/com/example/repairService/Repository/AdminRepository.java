package com.example.repairService.Repository;

import com.example.repairService.Model.Booking;
import com.example.repairService.Model.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdminRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AdminRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Booking> getAll(String sortType) {
        String sql = "Select * From \"BOOKING\" order by \"booking_date\"" + sortType;
        Map<String, String> param = new HashMap<>();
        param.put("SORT_TYPE", sortType);
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public List<Booking> getAllForUser(String username) {
        String sql = "Select  \"username\", \"note\", \"booking_date\", \"status\" From \"BOOKING\" Inner Join \"USERS\" On \"USERS\".\"id\" = \"BOOKING\".\"user_id\" Where \"USERS\".\"username\" Like :USERNAME";
        Map<String, String> param = new HashMap<>();
        param.put("USERNAME", "%" + username + "%");
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public boolean changeBookingStatus(long bookingId, Booking booking) {
        String sql = "Update \"BOOKING\" Set \"status\" = :STATUS Where \"id\" = :ID";
        Map<String, Object> params = new HashMap<>();

        params.put("STATUS", booking.getStatus());
        params.put("ID", bookingId);

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean changeProposalStatus(long proposalId, Proposal proposal) {
        String sql = "Update \"PROPOSAL\" Set \"status\" = :STATUS Where \"id\" = :ID";
        Map<String, Object> params = new HashMap<>();

        params.put("STATUS", proposal.isStatus());
        params.put("ID", proposalId);

        return jdbcTemplate.update(sql, params) == 1;
    }
}
