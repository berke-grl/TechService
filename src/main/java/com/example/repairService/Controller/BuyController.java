package com.example.repairService.Controller;

import com.example.repairService.Model.SaleLog;
import com.example.repairService.Repository.SaleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController()
@RequestMapping("/buy")
public class BuyController {

    private final SaleLogRepository saleLogRepository;

    @Autowired
    public BuyController(SaleLogRepository saleLogRepository) {
        this.saleLogRepository = saleLogRepository;
    }

    @PostMapping("/product")
    public ResponseEntity<String> buyProduct(@RequestBody SaleLog saleLog) {
        boolean result = saleLogRepository.save(saleLog);
        if (result)
            return ResponseEntity.ok("Buy Order Success");
        else
            return ResponseEntity.internalServerError().build();
    }
}
