package com.tatrabanka.sk.tatranumbers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private NumberAssignmentService numberAssignmentService;

    @GetMapping("/assign-number")
    public String assignNumber(@RequestParam("name") String name,
            @RequestParam(value = "number", required = false) String honeypot) {
        if (honeypot != null && !honeypot.isEmpty()) {
            return "Bot detected!";
        }

        if ("DominikMikuška".equalsIgnoreCase(name) || "Asenetka".equals(name) || "heslo123!".equalsIgnoreCase(name)) {
            numberAssignmentService.resetNumber();
            return "Čísla resetnuté.";
        }

        try {
            String assignedNumber = numberAssignmentService.assignNumber(name);
            return "Vaše čislo je: <b>" + assignedNumber + "</b>";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public String requestMethodName() {

        Map<String, String> map = numberAssignmentService.getMap();

        return new NumberList().getNumbers().stream().map(i -> {
            if (map.containsValue(i)) {
                return mapGreen(i);
            } else {
                return mapRed(i);
            }
        }).collect(Collectors.joining());
    }

    private String mapGreen(String text) {
        return "<p style=\"color:green;\">" + text + "</p>";
    }

    private String mapRed(String text) {
        return "<p style=\"color:red;\">" + text + "</p>";
    }

    // Endpoint to get the initial list of codes
    @GetMapping("/codes")
    public List<String> getCodes() {
        return numberAssignmentService.getNumList();
    }

    // Endpoint to process submitted codes
    @PostMapping("/submit-codes")
    public ResponseEntity<String> submitCodes(@RequestBody CodeRequest request) {
        String codes = request.getCodes();
        String password = request.getPassword();

        // Validate password
        if (!"DominikMikuška".equalsIgnoreCase(password) && !"heslo123!".equalsIgnoreCase(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        numberAssignmentService.setNumList(codes);
        // Perform processing, e.g., save to database, validate, etc.
        return ResponseEntity.ok("Miesta aktualizované");
    }

    @GetMapping("/grid")
    public Map<String, List<String>> getGridData() {
        // Example single list with mixed grid data
        List<String> gridData = numberAssignmentService.getMap().values().stream().toList();

        // Filter the data into sections
        Map<String, List<String>> filteredData = new HashMap<>();
        filteredData.put("B109", filterBySection(gridData, "B109"));
        filteredData.put("B108", filterBySection(gridData, "B108"));
        filteredData.put("B107", filterBySection(gridData, "B107"));

        return filteredData;
    }

    // Helper method to filter data by section prefix
    private List<String> filterBySection(List<String> gridData, String sectionPrefix) {
        return gridData.stream()
                .filter(s -> s.startsWith(sectionPrefix))
                .collect(Collectors.toList());
    }
}