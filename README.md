# Consistency Guarantees (Ver.1.0 — ChancePay)

This project follows an **outbox/inbox pattern** to approximate eventual consistency between the **orchestrator (request)** and the **wallet**.  
The guarantees below strictly match the current implementation.

---

### 1. Request is always recorded
- Once a client initiates a payment, a `Request` record is created, and an `Outbox` record is inserted in the same transaction.  
- As long as you hold the `uuid`, there is always at least a `Request`.  
- If the message has never been successfully published to MQ, the `Outbox` still retains the order.

---

### 2. Message publication is retried but bounded
- The outbox scanner retries publishing up to 5 times (`try_count < 5`).  
- If all 5 attempts fail, the message remains in the outbox. This is treated as a terminal PENDING case.  
- (DLQ and extended retry policy are left as TODO.)

---

### 3. Listener processes once, success or fail
- The wallet consumer wraps message handling in a try–catch block.  
- Any unhandled exception is swallowed, so the listener still returns normally and the broker sends an ACK.  
- This means a message is **never redelivered** (“at most once”): either the inbox + deduct succeed, or the message vanishes with an error log.  
- This design reflects the *ChancePay* philosophy: the wallet tries, but failure leaves only traces in logs, not repeated cooking of the same message.

---

### 4. If inbox insert + deduct succeed, request is notified (best effort)
- After updating inbox status (SUCCEED/FAILED), the listener attempts an HTTP `PUT /internal/requests/{uuid}` to update the request side.  
- This call is wrapped in `try–catch`. If it fails, wallet logs a warning and does not retry.  
- This reflects the *ChancePay* spirit: notification is attempted, but not guaranteed.

---

### 5. Idempotent consumption ensures no double deduct
- Inbox uses `INSERT IGNORE` with a unique key on `uuid`.  
- If a message is replayed, the insert returns 0 and the listener returns immediately.  
- This guarantees one payment is never deducted twice.

---

### 6. Request side may remain PENDING forever
- Reasons include:  
  - Outbox never successfully published (after 5 attempts).  
  - Wallet consumer failed and swallowed exception.  
  - Wallet succeeded but failed to notify orchestrator.  
- In all cases, the request remains visible as `PENDING` until resolved by manual reconciliation.  
- Logging is currently marked as TODO; future versions will log each case explicitly.

---

