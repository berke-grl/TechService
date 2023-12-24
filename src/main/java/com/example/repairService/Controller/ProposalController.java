package com.example.repairService.Controller;

import com.example.repairService.Model.Proposal;
import com.example.repairService.Repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    private final ProposalRepository proposalRepository;

    @Autowired
    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Proposal>> getAllForUser() {
        return ResponseEntity.ok(proposalRepository.getAllForUser());
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean result = proposalRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Your Proposal deleted successfully");
        else
            return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Proposal proposal) {
        boolean result = proposalRepository.save(proposal);
        if (result)
            return ResponseEntity.ok("Your Proposal send successfully");
        else
            return ResponseEntity.internalServerError().build();
    }
}
