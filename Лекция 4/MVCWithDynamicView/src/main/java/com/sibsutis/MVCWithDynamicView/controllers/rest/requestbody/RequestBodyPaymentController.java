package com.sibsutis.MVCWithDynamicView.controllers.rest.requestbody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class RequestBodyPaymentController {
    private Logger logger = Logger.getLogger(RequestBodyPaymentController.class.getName());

    @PostMapping("/request_payment")
    public ResponseEntity<PaymentDetails> makePayment(
            @RequestBody PaymentDetails paymentDetails
    ) {
        logger.info("Received payment: " +
                paymentDetails.getAmount());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentDetails);
    }
}
