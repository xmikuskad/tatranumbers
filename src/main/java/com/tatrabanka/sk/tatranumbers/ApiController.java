package com.tatrabanka.sk.tatranumbers;

import java.util.Map;
import java.util.stream.Collectors;

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

        if("DominikMikuška".equals(name) || "Asenetka".equals(name)) {
            numberAssignmentService.resetNumber();
            return "Čísla resetnuté.";
        }

        try {
            String assignedNumber = numberAssignmentService.assignNumber(name);
            return "Vaše čislo je: <b>" + assignedNumber+"</b>";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public String requestMethodName() {

        Map<String, String> map = numberAssignmentService.getMap();

        return new NumberList().getNumbers().stream().map(i-> {
            if(map.containsValue(i)) {
                return mapGreen(i);
            } else {
                return mapRed(i);
            }
        }).collect(Collectors.joining());
    }

    private String mapGreen(String text) {
        return "<p style=\"color:green;\">"+text+"</p>";
    }

    private String mapRed(String text) {
        return "<p style=\"color:red;\">"+text+"</p>";
    }
}