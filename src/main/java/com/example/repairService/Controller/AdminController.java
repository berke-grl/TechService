package com.example.repairService.Controller;

import com.example.repairService.Model.Booking;
import com.example.repairService.Repository.AdminRepository;
import com.example.repairService.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminRepository repository;

    @Autowired
    public AdminController(AdminRepository repository) {
        this.repository = repository;
    }

    @GetMapping("booking/getAll/{sort_type}")
    public ResponseEntity<List<Booking>> getAllBooking(@PathVariable String sort_type) {
        return ResponseEntity.ok(repository.getAll(sort_type));
    }

    @GetMapping("booking/getAll/user/{username}")
    public ResponseEntity<List<Booking>> getAllBookingForUser(@PathVariable String username){
        return ResponseEntity.ok(repository.getAllForUser(username));
    }
}
