package com.sibsutis.database.model;

import java.time.ZonedDateTime;

public record Feedback(int orderId, int productId, int customerId,
                       ZonedDateTime dateTime, short raiting, String feedbackText) {
}
