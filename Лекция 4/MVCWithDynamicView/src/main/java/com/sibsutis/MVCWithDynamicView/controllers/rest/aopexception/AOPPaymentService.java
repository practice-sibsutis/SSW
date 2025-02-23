package com.sibsutis.MVCWithDynamicView.controllers.rest.aopexception;

import org.springframework.stereotype.Service;

@Service
public class AOPPaymentService {
    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
