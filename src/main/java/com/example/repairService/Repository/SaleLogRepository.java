package com.example.repairService.Repository;

import com.example.repairService.Model.SaleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SaleLogRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public SaleLogRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public boolean save(SaleLog saleLog) {
        String sql = "Insert Into \"SALE_LOG\"(\"sale_log_date\", \"credit_card\", \"sale_id\", \"user_id\") Values(:SALE_LOG_DATE, :CREDIT_CARD, :SALE_ID, :USER_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("SALE_LOG_DATE", new Date());
        params.put("CREDIT_CARD", saleLog.getCredit_card());
        params.put("SALE_ID", saleLog.getSale_id());
        params.put("USER_ID", userRepository.getCurrentUserId());

        boolean result = jdbcTemplate.update(sql, params) == 1;

        if (result) {
            String sqlSale = "Delete From \"SALE\" Where \"id\" = :ID";
            Map<String, Long> param = new HashMap<>();
            param.put("ID", saleLog.getSale_id());

            return jdbcTemplate.update(sqlSale, param) == 1;
        } else {
            return false;
        }
    }
}
