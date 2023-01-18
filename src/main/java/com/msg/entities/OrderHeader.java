package com.msg.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@AttributeOverrides(
        {@AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        ), @AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_city")
        ), @AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_state")
        ), @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        ), @AttributeOverride(
                name = "billToAddress.address",
                column = @Column(name = "bill_to_address")
        ), @AttributeOverride(
                name = "billToAddress.city",
                column = @Column(name = "bill_to_city")
        ), @AttributeOverride(
                name = "billToAddress.state",
                column = @Column(name = "bill_to_state")
        ), @AttributeOverride(
                name = "billToAddress.zipCode",
                column = @Column(name = "bill_to_zip_code")
        )}
)
public class OrderHeader extends BaseEntity {

    private String customer;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billToAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderHeader that = (OrderHeader) o;

        if (!customer.equals(that.customer)) return false;
        if (!shippingAddress.equals(that.shippingAddress)) return false;
        return billToAddress.equals(that.billToAddress);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + shippingAddress.hashCode();
        result = 31 * result + billToAddress.hashCode();
        return result;
    }
}
