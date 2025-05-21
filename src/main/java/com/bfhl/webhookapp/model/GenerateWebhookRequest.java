package com.bfhl.webhookapp.model;

import java.io.Serializable;

public class GenerateWebhookRequest implements Serializable {

    private String name;
    private String regNo;
    private String email;

    public GenerateWebhookRequest() {}

    public GenerateWebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
