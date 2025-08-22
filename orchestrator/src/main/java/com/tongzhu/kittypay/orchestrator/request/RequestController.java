package com.tongzhu.kittypay.orchestrator.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api")

public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/payments")
    public ResponseEntity<Request> postPaymentRequest(@RequestBody RequestInputDTO requestInputDTO) {
        String uuid = UUID.randomUUID().toString();
        Request request = requestService.postPaymentRequest(uuid, requestInputDTO);

        return ResponseEntity.ok(request);

    }


}
