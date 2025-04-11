create table if not exists departments(
    id int primary key auto_increment,
    name varchar(50) unique not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists employees(
    id int primary key auto_increment,
    firstname varchar(50) not null,
    lastname varchar(50) not null,
    email varchar(100) unique not null,
    phone varchar(11),
    joining_date date not null,
    department int not null,
    salary decimal(10,2) not null,
    location varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    foreign key(department) references departments(id)
);

insert into departments (name)
select * from (select 'Information Technology' as name) as tmp
where not exists (select 1 from departments where name = 'Information Technology');

insert into departments (name)
select * from (select 'Marketing' as name) as names
where not exists (select 1 from departments where name = 'Marketing');

insert into departments (name)
select * from (select 'Finance' as name) as names
where not exists (select 1 from departments where name = 'Finance');

insert into departments (name)
select * from (select 'HR' as name) as names
where not exists (select 1 from departments where name = 'HR');

create index if not exists index_employees_department on employees(department);
create index if not exists index_employees_joining_date on employees(joining_date);
create index if not exists index_employees_names on employees(firstname,lastname);
create index if not exists index_employees_salary on employees(salary);
create index if not exists index_employees_location on employees(location);
create unique index if not exists index_employees_phone on employees(phone)

