package com.tongzhu.kittypay.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class JsonConverter {


    private static final ObjectMapper objectMapper = new ObjectMapper();




    public static String extractUuid(String payload) {
        try {

            return objectMapper.readTree(payload).get("uuid").asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract uuid", e);
        }
    }


    public static String extractCusId(String payload) {
        try {

            return objectMapper.readTree(payload).get("cusId").asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract cusId", e);
        }
    }


    public static BigDecimal extractAmount(String payload) {
        try {

            return objectMapper.readTree(payload).get("amount").decimalValue();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract amount", e);
        }
    }




}
