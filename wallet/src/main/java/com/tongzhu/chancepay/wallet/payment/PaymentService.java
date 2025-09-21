package com.tongzhu.chancepay.wallet.payment;

import com.tongzhu.chancepay.wallet.inbox.InboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Transactional
public class PaymentService {

    private static final Random random = new Random();
    private final InboxRepository inboxRepository;

    public PaymentService(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    public Boolean pretendToProcess(String uuid, String cusId, BigDecimal amount) {

        if (inboxRepository.insertIgnore(uuid) == 0) return null; // uuid existed!

        else {
            Boolean deducted = random.nextBoolean();
            inboxRepository.updateStatus(uuid, deducted ? "SUCCEED" : "FAILED");
            return deducted;
        }

    }
}
