package com.example.repairService.Repository;

import com.example.repairService.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookingRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public BookingRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public long getCurrentId() {
        long numb = jdbcTemplate.queryForObject("Select COUNT(*) From \"BOOKING\"", new MapSqlParameterSource(), Long.class);
        return numb++;
    }

    public List<Booking> getAll() {
        return jdbcTemplate.query("Select * From \"BOOKING\"", BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public Booking getById(long id) {
        String sql = "Select * From \"BOOKING\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public boolean deleteById(long id) {
        long currentUserId = userRepository.getCurrentUserId();
        Booking booking = getById(id);
        if (booking.getUser_id() == currentUserId) {
            String sql = "Delete From \"BOOKING\" Where \"id\" = :ID";
            Map<String, Long> param = new HashMap<>();
            param.put("ID", id);

            return jdbcTemplate.update(sql, param) == 1;
        } else {
            return false;
        }
    }

    public boolean save(Booking booking) throws ParseException {

        String sql = "INSERT INTO \"BOOKING\"(\"note\", \"booking_date\", \"status\", \"service_id\", \"user_id\") VALUES (:NOTE, :BOOKING_DATE, :STATUS, :SERVICE_ID, :USER_ID)";
        Map<String, Object> params = new HashMap<>();

        // Step 1: Get current LocalDateTime
        LocalDateTime now = LocalDateTime.now();

        // Step 2: Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        // Step 3: Parse the string back to a Date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDate = sdf.parse(formattedDateTime);

        // Step 4: Convert Date to Timestamp for PostgreSQL
        java.sql.Timestamp sqlTimestamp = new Timestamp(parsedDate.getTime());

        params.put("NOTE", booking.getNote());
        params.put("BOOKING_DATE", sqlTimestamp);
        params.put("STATUS", booking.getStatus());
        params.put("SERVICE_ID", booking.getService_id());
        params.put("USER_ID", booking.getUser_id());

        return jdbcTemplate.update(sql, params) == 1;
    }
}













