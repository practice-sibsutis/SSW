package com.sibsutis.MVCWithDynamicView.controllers.rest.responsestatus;

public class Country {
    private String name;
    private int population;

    private Country() {}

    public static Country of(String name, int population) {
        var country = new Country();
        country.setName(name);
        country.setPopulation(population);
        return country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}