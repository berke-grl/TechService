package com.example.repairService.Controller;

import com.example.repairService.Model.Sale;
import com.example.repairService.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SaleRepository saleRepository;

    @Autowired
    public SaleController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Sale>> getAll() {
        return ResponseEntity.ok(saleRepository.getAll());
    }
}
