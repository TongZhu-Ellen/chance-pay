package com.tongzhu.chancepay.orchestrator.request;

import java.math.BigDecimal;

public record RequestInputDTO(String cusId, BigDecimal amount) {
}
