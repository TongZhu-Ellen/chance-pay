package com.tongzhu.kittypay.orchestrator.request;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.tongzhu.kittypay.orchestrator.CreateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;


    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<Request> postRequest(@RequestBody CreateRequestDto dto) throws JsonProcessingException {
        Request request = requestService.postRequest(dto);
        return ResponseEntity.ok(request);
    }


}
