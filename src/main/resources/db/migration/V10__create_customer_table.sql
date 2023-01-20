drop table if exists customer cascade;

create table customer
(
    id        bigint not null auto_increment primary key,
    customer_name      varchar(50),
    address varchar(30),
    city varchar(30),
    state varchar(30),
    zip_code varchar(30),
    phone varchar(20),
    email varchar(255),
    created_date timestamp,
    last_modified_date timestamp
) engine = InnoDB;

alter table order_header add column customer_id bigint;
alter table order_header add constraint customer_id_fk foreign key (customer_id) references customer(id);
alter table order_header drop column customer;
alter table customer add column order_header_id bigint;
alter table customer add constraint order_header_id_fk FOREIGN KEY (order_header_id) REFERENCES order_header(id);

insert into customer(customer_name, address, city, state, zip_code, phone, email, created_date, last_modified_date)
values("Customer1", "Address1", "City1", "State1", "Z1", "123456789", "test1@test.test", now(), now());

insert into order_header(order_status, created_date, last_modified_date, customer_id)
values("NEW", now(), now(), (SELECT id FROM customer c WHERE c.customer_name = 'Customer1'));

update order_header set order_header.customer_id = (select id from customer limit 1);