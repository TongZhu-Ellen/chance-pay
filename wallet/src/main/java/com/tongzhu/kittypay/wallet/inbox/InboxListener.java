package com.tongzhu.kittypay.wallet.inbox;

import com.tongzhu.kittypay.wallet.JsonConverter;
import com.tongzhu.kittypay.wallet.Status;
import com.tongzhu.kittypay.wallet.wallet.WalletRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class InboxListener {

    static final String QUEUE_NAME = "kitty_queue";

    private final InboxRepository inboxRepository;
    private final WalletRepository walletRepository;

    public InboxListener(InboxRepository inboxRepository, WalletRepository walletRepository) {
        this.inboxRepository = inboxRepository;
        this.walletRepository = walletRepository;
    }


    @Transactional
    @RabbitListener(queues = QUEUE_NAME)
    public void inboxListener(String payload) {

        String uuid = JsonConverter.extractUuid(payload);
        String cusId = JsonConverter.extractCusId(payload);
        BigDecimal amount = JsonConverter.extractAmount(payload);

        if (inboxRepository.insertIgnore(uuid) == 0) return;

        boolean deductionSucceed = walletRepository.tryDeduct(cusId, amount) == 1;

        inboxRepository.findById(uuid).get().setStatus(deductionSucceed ? Status.SUCCEED : Status.FAILED);










    }
}
