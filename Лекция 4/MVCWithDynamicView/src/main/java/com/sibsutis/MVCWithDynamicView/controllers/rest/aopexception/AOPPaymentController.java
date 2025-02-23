package com.sibsutis.MVCWithDynamicView.controllers.rest.aopexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AOPPaymentController {

    private final AOPPaymentService paymentService;

    public AOPPaymentController(AOPPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/aop_payment")
    public ResponseEntity<PaymentDetails> makePayment() {
        var paymentDetails = paymentService.processPayment();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentDetails);
    }
}
