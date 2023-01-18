package com.msg.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Stock {

    private Integer quantity;

    @ManyToMany(mappedBy = "stocks")
    private List<Location> locations = new ArrayList<>();

    @OneToOne(mappedBy = "stock")
    private Product product;

    public Stock(Integer quantity) {
        this.quantity = quantity;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void removeLocation(Location location) {
        this.locations.remove(location);
    }
}
