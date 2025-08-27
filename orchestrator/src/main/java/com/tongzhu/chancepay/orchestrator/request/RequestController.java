package com.tongzhu.chancepay.orchestrator.request;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody RequestInputDto paymentInputDto) {

        String uuid = UUID.randomUUID().toString();

        requestService.postRequest(uuid, paymentInputDto);

        return ResponseEntity.accepted().body(uuid);

    }


    @GetMapping("/{uuid}")
    public ResponseEntity<Request> getRequest(@PathVariable String uuid) {
        Optional<Request> optionalRequest = requestService.getRequest(uuid);

        return optionalRequest
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());


    }





}
