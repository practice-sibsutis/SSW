package com.sibsutis.database.Service;

import com.sibsutis.database.model.Shop;

public class ShopFactory {
    private int id;

    public ShopFactory(int id) {
        this.id = id;
    }

    public Shop createShop(String address, String directorName, String phone, String workingTime) {
        Shop shop = new Shop(id, address, directorName, phone, workingTime);
        ++id;
        return shop;
    }
}
