package com.bfhl.webhookapp.service;

import com.bfhl.webhookapp.model.GenerateWebhookRequest;
import com.bfhl.webhookapp.model.GenerateWebhookResponse;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService implements ApplicationRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(ApplicationArguments args) {
        try {
            // 1. Create request body
            GenerateWebhookRequest requestPayload = new GenerateWebhookRequest();
            requestPayload.setName("Rozal Khan");        
            requestPayload.setRegNo("1031");            
            requestPayload.setEmail("1032221031@mitwpu.edu.in");  
            // 2. Send POST to generate webhook
            ResponseEntity<GenerateWebhookResponse> response = restTemplate.postForEntity(
                    "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA",
                    requestPayload,
                    GenerateWebhookResponse.class
            );

            String webhookUrl = response.getBody().getWebhookUrl();
            String accessToken = response.getBody().getAccessToken();

            System.out.println("✅ Webhook URL: " + webhookUrl);
            System.out.println("🔐 Access Token: " + accessToken);

            // 3. Your SQL query to send
            String finalQuery = String finalQuery = "SELECT p.amount AS SALARY, CONCAT(e.first_name, ' ', e.last_name) AS NAME, TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS AGE, d.department_name AS DEPARTMENT_NAME FROM payments p JOIN employee e ON p.emp_id = e.emp_id JOIN department d ON e.department = d.department_id WHERE DAY(p.payment_time) != 1 ORDER BY p.amount DESC LIMIT 1;";
; 

            // 4. Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken); // adds Authorization: Bearer <token>

            // 5. Prepare body
            JSONObject body = new JSONObject();
            body.put("finalQuery", finalQuery);

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

            // 6. Send SQL to webhook
            ResponseEntity<String> submissionResponse = restTemplate.postForEntity(webhookUrl, entity, String.class);

            System.out.println("🚀 Submission Response: " + submissionResponse.getStatusCode());
            System.out.println("✅ Server Response: " + submissionResponse.getBody());

        } catch (Exception e) {
            System.out.println("🔥 Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
