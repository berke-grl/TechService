package com.example.repairService.Repository;

import com.example.repairService.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.*;

@Repository
public class BookingRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public BookingRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    public long getCurrentId() {
        long numb = jdbcTemplate.queryForObject("Select \"id\" From \"BOOKING\" Order By \"id\" desc Limit 1", new MapSqlParameterSource(), Long.class);
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

        Date date = getBookingDate(booking);

        String sql = "INSERT INTO \"BOOKING\"(\"note\", \"booking_date\", \"status\", \"service_id\", \"user_id\") VALUES (:NOTE, :BOOKING_DATE, :STATUS, :SERVICE_ID, :USER_ID)";
        Map<String, Object> params = new HashMap<>();

        params.put("NOTE", booking.getNote());
        params.put("BOOKING_DATE", date);
        params.put("STATUS", "Bekliyor"); //according to the doc record must be bekliyor at the beginning
        params.put("SERVICE_ID", booking.getService_id());
        params.put("USER_ID", booking.getUser_id());

        return jdbcTemplate.update(sql, params) == 1;
    }

    public int sumOfHours(Date date) {
        String sql = "Select SUM(\"duration\") From \"SERVICE\" Inner Join \"BOOKING\" On \"BOOKING\".\"service_id\" = \"SERVICE\".\"id\" Where \"BOOKING\".\"booking_date\" = :SYSTEM_DATE";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("SYSTEM_DATE", date); // Add the parameter to the MapSqlParameterSource

        Integer sum = jdbcTemplate.queryForObject(sql, parameters, Integer.class); // Use the parameters object
        if (sum == null) {
            return 0; // Or handle null differently if required
        }
        return sum;
    }

    public boolean isAvailable(Booking booking, Date date) {
        int currentBookingDuration = serviceRepository.getServiceDurationById(booking.getService_id());
        int totalBookingDurationInQueue = sumOfHours(date);

        if (totalBookingDurationInQueue + currentBookingDuration > 10)
            return false;
        else
            return true;
    }

    public Date getBookingDate(Booking booking) {
        Date date = new Date(); // Start with the current date
        Calendar calendar = Calendar.getInstance();

        while (true) {
            if (isAvailable(booking, date)) {
                return date; // Return the date if it has enough available hours
            }
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
            date = calendar.getTime();
        }
    }


    public Date getSystemAvailableDate() {
        String sql = "Select \"booking_date\" From \"BOOKING\" Order By \"booking_date\" Desc Limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Date.class);
        } catch (EmptyResultDataAccessException e) {
            return new Date(); // Return current date if no rows are found
        }
    }
}













