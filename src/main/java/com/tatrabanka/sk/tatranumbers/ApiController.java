package com.tatrabanka.sk.tatranumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "Hello, " + name + "!";
    }

        @Autowired
    private NumberAssignmentService numberAssignmentService;

    @GetMapping("/assign-number")
    public String assignNumber(@RequestParam String name) {
        try {
            Integer assignedNumber = numberAssignmentService.assignNumber(name);
            return "Hello, " + name + "! Your assigned number is: " + assignedNumber;
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}