create table if not exists employees(
    id int primary key auto_increment,
    firstname varchar(50) not null,
    lastname varchar(50) not null,
    email varchar(100) unique not null,
    phone varchar(11),
    joining_date date not null,
    department varchar(50) not null,
    salary decimal(10,2) not null,
    location varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp 
);

create index if not exists index_employees_department on employees(department);
create index if not exists index_employees_joining_date on employees(joining_date);
create index if not exists index_employees_names on employees(firstname,lastname);
create index if not exists index_employees_salary on employees(salary);
create index if not exists index_employees_location on employees(location);
create unique index if not exists index_employees_phone on employees(phone)
