package com.tongzhu.chancepay.orchestrator.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/requests")
public class RequestInternalController {

    private final RequestService requestService;
    private final static Logger log = LoggerFactory.getLogger(RequestInternalController.class);

    public RequestInternalController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<Void> setSucceedOrFailed(@PathVariable String uuid, @RequestBody boolean succeed) {

       boolean set = requestService.setSucceedOrFailed(uuid, succeed) == 1;

       if (!set) {
           log.error("[BUSINESS_DIVERGENCE_ALERT] | request {} does not exists or not PENDING", uuid);
       }

       return ResponseEntity.accepted().build();

    }



}
