drop table if exists category cascade;

create table category
(
    id        bigint not null auto_increment primary key,
    description      varchar(255),
    created_date timestamp,
    last_modified_date timestamp
) engine = InnoDB;

drop table if exists product_category cascade;

create table product_category
(
    product_id        bigint not null,
    category_id        bigint not null,
    primary key (product_id, category_id),
    constraint pc_product_id_fk FOREIGN KEY (product_id) REFERENCES product(id),
    constraint pc_category_id_fk FOREIGN KEY (category_id) REFERENCES category(id)
) engine = InnoDB;

#inserting values into product table
insert into product(description, product_status, created_date, last_modified_date)
values("Product 1", "NEW", now(), now());
insert into product(description, product_status, created_date, last_modified_date)
values("Product 2", "NEW", now(), now());
insert into product(description, product_status, created_date, last_modified_date)
values("Product 3", "NEW", now(), now());
insert into product(description, product_status, created_date, last_modified_date)
values("Product 4", "NEW", now(), now());

#inserting values into category table
insert into category(description, created_date, last_modified_date)
values("Category 1", now(), now());
insert into category(description, created_date, last_modified_date)
values("Category 2", now(), now());
insert into category(description, created_date, last_modified_date)
values("Category 3", now(), now());

#inserting values into product_category table
insert into product_category(product_id, category_id)
SELECT p.id, c.id FROM product p, category c
WHERE p.description = 'Product 1' AND c.description = 'Category 1';
insert into product_category(product_id, category_id)
SELECT p.id, c.id FROM product p, category c
WHERE p.description = 'Product 2' AND c.description = 'Category 1';
insert into product_category(product_id, category_id)
SELECT p.id, c.id FROM product p, category c
WHERE p.description = 'Product 1' AND c.description = 'Category 3';
insert into product_category(product_id, category_id)
SELECT p.id, c.id FROM product p, category c
WHERE p.description = 'Product 4' AND c.description = 'Category 3';

