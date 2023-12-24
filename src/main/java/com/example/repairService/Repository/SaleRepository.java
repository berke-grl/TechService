package com.example.repairService.Repository;

import com.example.repairService.Model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SaleRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SaleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sale> getAll() {
        return jdbcTemplate.query("Select * From \"SALE\"", BeanPropertyRowMapper.newInstance(Sale.class));
    }

    public Sale getById(long id) {
        String sql = "Select * From \"SALE\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Sale.class));
    }

    //SELECT name FROM public."PRODUCT" INNER Join public."SALE" ON "SALE".product_id = "PRODUCT".id WHERE "PRODUCT"."name" = 'Motherboard';
    public List<Sale> getSaleByProductName(String product_name) {
        String sql = "Select * From \"PRODUCT\" Inner Join \"SALE\" On \"SALE\".\"product_id\" = \"PRODUCT\".\"id\" Where \"PRODUCT\".\"name\" = :PRODUCT_NAME";
        Map<String, String> param = new HashMap<>();
        param.put("PRODUCT_NAME", product_name);

        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Sale.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteById(long id) {
        String sql = "Delete From \"SALE\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.update(sql, param) == 1;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean save(Sale sale) {
        String sql = "Insert Into \"SALE\"(\"note\", \"price\", \"product_id\") Values(:NOTE, :PRICE, :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", sale.getNote());
        params.put("PRICE", sale.getPrice());
        params.put("PRODUCT_ID", sale.getProduct_id());

        return jdbcTemplate.update(sql, params) == 1;
    }
}
