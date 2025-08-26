package com.tongzhu.chancepay.wallet.inbox;

import com.tongzhu.chancepay.wallet.JsonConverter;
import com.tongzhu.chancepay.wallet.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class InboxService {



    private final RestTemplate restTemplate= new RestTemplate();

    private final InboxRepository inboxRepository;
    private final WalletRepository walletRepository;

    public InboxService(InboxRepository inboxRepository, WalletRepository walletRepository) {
        this.inboxRepository = inboxRepository;
        this.walletRepository = walletRepository;
    }



    @Transactional
    public void process(String payload) {



        String uuid = JsonConverter.extractUuid(payload);
        String cusId = JsonConverter.extractCusId(payload);
        BigDecimal amount = JsonConverter.extractAmount(payload);


        boolean existed = inboxRepository.insertIgnore(uuid) == 0;

        if (existed) return;

        boolean deducted = walletRepository.deduct(cusId, amount) == 1;

        inboxRepository.updateStatus(uuid, deducted ? "SUCCEED" : "FAILED");

        try {
            restTemplate.put(
                    "http://localhost:8080/internal/requests/" + uuid,
                    deducted);
        } catch (Exception e) {
            // TODO: log.warn() to warn about failed HTTP-req;
        }

    }



}
