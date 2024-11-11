package com.tatrabanka.sk.tatranumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private NumberAssignmentService numberAssignmentService;

    @GetMapping("/assign-number")
    public String assignNumber(@RequestParam("name") String name, @RequestParam(value = "number", required = false) String honeypot) {
        if (honeypot != null && !honeypot.isEmpty()) {
            return "Bot detected!";
        }

        if("DominikMikuška".equals(name)) {
            numberAssignmentService.resetNumber();
            return "Čísla resetnuté.";
        }

        try {
            Integer assignedNumber = numberAssignmentService.assignNumber(name);
            return "Vaše čislo je: <b>" + assignedNumber+"</b>";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}