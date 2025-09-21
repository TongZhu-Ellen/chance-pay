package com.tongzhu.chancepay.wallet.response;

import com.tongzhu.chancepay.wallet.inbox.InboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Transactional
public class ResponseService {

    private static final Random random = new Random();
    private static final String CALLBACK_URL = "http://localhost:8080/internal/requests/";
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger log = LoggerFactory.getLogger(ResponseService.class);


    private final InboxRepository inboxRepository;

    public ResponseService(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }







    /**
     * Pretend to process a payment:
     * - insert into inbox (pending)
     * - randomly decide succeed/failed
     * - update inbox
     * - callback orchestrator
     *
     * The method name deliberately highlights that Wallet here is a stub.
     */


    public void pretendToProcess(String uuid, String cusId, BigDecimal amount) {

        if (inboxRepository.insertIgnore(uuid) == 0) return; // uuid existed!

        Boolean succeed = random.nextBoolean();
        inboxRepository.updateStatus(uuid, succeed);

        try {
            restTemplate.put(
                    CALLBACK_URL + uuid,
                    succeed);
        } catch (Exception callbackSendingExp) {
            log.warn("[FAILED TO CALLBACK FROM WALLET] | url: {}, uuid: {}, cusId: {}, amount: {}, deducted: {}",
                    CALLBACK_URL + uuid, uuid, cusId, amount, succeed,
                    callbackSendingExp);
        }



    }


}
