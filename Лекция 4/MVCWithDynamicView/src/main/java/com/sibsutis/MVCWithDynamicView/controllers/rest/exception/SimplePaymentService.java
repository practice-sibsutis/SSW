package com.sibsutis.MVCWithDynamicView.controllers.rest.exception;

import org.springframework.stereotype.Service;

@Service
public class SimplePaymentService {
    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
