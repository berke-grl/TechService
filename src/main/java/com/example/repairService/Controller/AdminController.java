package com.example.repairService.Controller;

import com.example.repairService.Model.Booking;
import com.example.repairService.Model.Proposal;
import com.example.repairService.Model.Sale;
import com.example.repairService.Repository.AdminRepository;
import com.example.repairService.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminRepository adminRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public AdminController(AdminRepository repository, SaleRepository saleRepository) {
        this.adminRepository = repository;
        this.saleRepository = saleRepository;
    }

    // BOOKING FUNCTİONALİTY FOR ADMIN
    @GetMapping("/booking/getAll/{sort_type}")
    public ResponseEntity<List<Booking>> getAllBooking(@PathVariable String sort_type) {
        return ResponseEntity.ok(adminRepository.getAll(sort_type));
    }

    @GetMapping("/booking/getAll/user/{username}")
    public ResponseEntity<List<Booking>> getAllBookingForUser(@PathVariable String username) {
        return ResponseEntity.ok(adminRepository.getAllForUser(username));
    }

    @PutMapping("/booking/status/{booking_id}")
    public ResponseEntity<String> changeBookingStatus(@PathVariable long booking_id, @RequestBody Booking booking) {
        boolean result = adminRepository.changeBookingStatus(booking_id, booking);
        if (result)
            return ResponseEntity.ok("Booking ID -> " + booking_id + " Status -> " + booking.getStatus());
        else
            return ResponseEntity.internalServerError().build();
    }

    // SALE FUNCTİONALİTY FOR ADMIN
    @DeleteMapping("sale/deleteById/{id}")
    public ResponseEntity<String> saleDeleteById(@PathVariable long id) {
        boolean result = saleRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Sale Record Deleted ID-> " + id);
        else
            return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/sale/save")
    public ResponseEntity<Sale> saleProductForAdmin(@RequestBody Sale sale) {
        boolean result = saleRepository.save(sale);
        if (result)
            return ResponseEntity.ok(sale);
        else
            return ResponseEntity.internalServerError().build();
    }

    // PROPOSAL FUNCTİONALİTY FOR ADMIN
    @PutMapping("/proposal/status/{id}")
    public ResponseEntity<String> changeProposalStatus(@PathVariable long id, @RequestBody Proposal proposal) {
        boolean result = adminRepository.changeProposalStatus(id, proposal);
        if (result)
            return ResponseEntity.ok("Proposal status changed to ->" + proposal.isStatus());
        else
            return ResponseEntity.internalServerError().build();
    }
}
