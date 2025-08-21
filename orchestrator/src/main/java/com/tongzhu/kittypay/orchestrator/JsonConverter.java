package com.tongzhu.kittypay.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.math.BigDecimal;
import java.util.Map;


public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();




    public static String toPayload(String uuid,
                         String cusID,
                         BigDecimal amount) {
        try {

            return objectMapper.writeValueAsString(
                    Map.of("uuid", uuid,
                            "customerId", cusID,
                            "amount", amount)



            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to build payload", e);
        }
    }




    public static String extractUuid(String payload) {
        try {


            return objectMapper.readTree(payload).get("uuid").asText();


        } catch (Exception e) {
            throw new RuntimeException("Failed to extract uuid", e);
        }
    }
}
