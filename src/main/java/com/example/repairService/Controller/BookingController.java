package com.example.repairService.Controller;

import com.example.repairService.Model.Booking;
import com.example.repairService.Repository.BookingRepository;
import com.example.repairService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository repository, UserRepository userRepository) {
        this.bookingRepository = repository;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Booking> getById(@PathVariable long id) {
        return ResponseEntity.ok(bookingRepository.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Long> appointment(@RequestBody Booking booking) {
        try {
            bookingRepository.save(booking);

            return ResponseEntity.ok(bookingRepository.getCurrentId());
        } catch (ParseException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean result = bookingRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Booking Deleted Successfully");
        else
            return ResponseEntity.badRequest().body("You can not remove this Booking");
    }
}
