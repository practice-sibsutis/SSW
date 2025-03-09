package com.sibsutis.database.model;

import java.time.ZonedDateTime;

public record Order(int id, int productId, int customerId, int quantity,
                    int amount, int shopId, ZonedDateTime orderingTime) {
}
