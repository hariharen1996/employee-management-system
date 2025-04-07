create table if not exists employees(
    id int primary key auto_increment,
    firstname varchar(50) not null,
    lastname varchar(50) not null,
    email varchar(100) unique not null,
    phone varchar(11),
    joining_date date not null,
    department varchar(20) not null,
    salary decimal(10,2) not null,
    location varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);