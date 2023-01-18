package com.msg.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Address {

    private String address;
    private String city;
    private String state;
    private String zipCode;
}
