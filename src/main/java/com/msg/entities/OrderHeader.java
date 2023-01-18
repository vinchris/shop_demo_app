package com.msg.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderHeader that = (OrderHeader) o;

        if (!Objects.equals(customer, that.customer)) return false;
        if (!Objects.equals(shippingAddress, that.shippingAddress))
            return false;
        if (!Objects.equals(billToAddress, that.billToAddress))
            return false;
        return orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (billToAddress != null ? billToAddress.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
