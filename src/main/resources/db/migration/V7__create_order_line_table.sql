drop table if exists order_line cascade;

create table order_line
(
    id        bigint not null auto_increment primary key,
    quantity_ordered      int,
    order_header_id bigint,
    created_date timestamp,
    last_modified_date timestamp,
    constraint order_header_fk FOREIGN KEY (order_header_id) references order_header(id)
) engine = InnoDB;