package com.msg.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Location {

    private @Id
    @GeneratedValue Long id;

    private String name;
    private String country;
    private String county;
    private String city;
    private String cityAddress;

    public Location(String name, String country, String county, String city, String cityAddress) {
        this.name = name;
        this.country = country;
        this.county = county;
        this.city = city;
        this.cityAddress = cityAddress;
    }

    @ManyToMany
    private List<Stock> stocks = new ArrayList<>();

    public void addLocation(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeLocation(Stock stock) {
        this.stocks.remove(stock);
    }
}
