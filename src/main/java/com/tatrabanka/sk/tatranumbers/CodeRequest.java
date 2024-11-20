package com.tatrabanka.sk.tatranumbers;

// Create a DTO for the incoming payload
public class CodeRequest {
    private String codes;
    private String password;

    // Getters and setters (required for JSON deserialization)
    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
