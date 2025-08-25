package com.tongzhu.chancepay.orchestrator.request;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/requests")
public class RequestInternalController {

    private final RequestService requestService;

    public RequestInternalController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PutMapping("/{uuid}")
    public void setSucceedOrFailed(@PathVariable String uuid, @RequestBody boolean succeed) {

       boolean set = requestService.setSucceedOrFailed(uuid, succeed) == 1;

       if (!set) {
           // TODO: log.error here!
       }

    }



}
