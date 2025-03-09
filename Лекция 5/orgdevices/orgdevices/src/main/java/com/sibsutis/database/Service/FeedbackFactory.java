package com.sibsutis.database.Service;

import com.sibsutis.database.model.Customer;
import com.sibsutis.database.model.Feedback;
import com.sibsutis.database.model.Order;
import com.sibsutis.database.model.Product;

import java.time.ZonedDateTime;

public class FeedbackFactory {
    public Feedback createFeedback(Order order, Customer customer, Product product,
                                   ZonedDateTime date, short raiting, String comment) {
        return new Feedback(order.id(), product.id(), customer.id(), date, raiting, comment);
    }
}
