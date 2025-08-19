package com.tongzhu.kittypay.orchestrator;

import java.math.BigDecimal;

public record CreateRequestDto(String cusId, BigDecimal amount) {
}
