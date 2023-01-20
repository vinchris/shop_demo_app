package com.msg.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer extends BaseEntity {

    private String customerName;

    @Embedded
    private Address address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<OrderHeader> orderHeaders = new ArrayList<>();

    public void addOrder(OrderHeader orderHeader) {
        if (orderHeaders == null) {
            orderHeaders = new ArrayList<>();
        }
        orderHeaders.add(orderHeader);
        orderHeader.setCustomer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(customerName, customer.customerName))
            return false;
        if (!Objects.equals(address, customer.address)) return false;
        if (!Objects.equals(phone, customer.phone)) return false;
        if (!Objects.equals(email, customer.email)) return false;
        return Objects.equals(orderHeaders, customer.orderHeaders);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
