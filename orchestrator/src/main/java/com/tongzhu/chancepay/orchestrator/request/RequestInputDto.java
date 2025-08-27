package com.tongzhu.chancepay.orchestrator.request;

import java.math.BigDecimal;

public record RequestInputDto(String cusId, BigDecimal amount) {
}
