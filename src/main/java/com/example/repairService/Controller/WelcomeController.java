package com.example.repairService.Controller;

import com.example.repairService.Model.SystemUser;
import com.example.repairService.Repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @GetMapping("/hi")
    public String sayHi() {
        return "hello world";
    }
}
