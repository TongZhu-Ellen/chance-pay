package com.tongzhu.chancepay.wallet.payment;


import com.tongzhu.chancepay.wallet.inbox.InboxRepository;
import com.tongzhu.chancepay.wallet.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

@Service
public class PaymentService {



    private final InboxRepository inboxRepository;
    private final WalletRepository walletRepository;

    public PaymentService(InboxRepository inboxRepository, WalletRepository walletRepository) {
        this.inboxRepository = inboxRepository;
        this.walletRepository = walletRepository;
    }












    @Transactional
    public boolean process(String uuid, String cusId, BigDecimal amount) {






        boolean existed = inboxRepository.insertIgnore(uuid) == 0;

        if (existed) return false;

        boolean deducted = walletRepository.tryDeduct(cusId, amount) == 1;

        inboxRepository.updateStatus(uuid, deducted ? "SUCCEED" : "FAILED");

        return deducted;

    }
}
