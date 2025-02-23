package com.sibsutis.MVCWithDynamicView.controllers.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePaymentController {

    private final SimplePaymentService paymentService;

    public SimplePaymentController(SimplePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/simple_payment")
    public ResponseEntity<?> makePayment() {
        try {
            var paymentDetails = paymentService.processPayment();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(paymentDetails);
        } catch (NotEnoughMoneyException e) {
            var errorDetail = new ErrorDetail();
            errorDetail.setMessage("Not enough money to make the payment.");
            return ResponseEntity
                    .badRequest()
                    .body(errorDetail);
        }
    }
}
