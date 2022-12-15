
DROP DATABASE IF EXISTS car_leasing;
CREATE DATABASE car_leasing;
USE car_leasing;


DROP TABLE IF EXISTS fuel;
CREATE TABLE fuel (
car_fuel_type VARCHAR(20)  PRIMARY KEY,
distance_unit VARCHAR(20)
);


DROP TABLE IF EXISTS car_model;
CREATE TABLE car_model (
car_model_id INT PRIMARY KEY AUTO_INCREMENT,
car_model VARCHAR(30) NOT NULL,
car_hp INT NOT NULL,
car_brand VARCHAR(30) NOT NULL,
car_fuel_type VARCHAR(20) NOT NULL,
car_gearbox_type VARCHAR(20) NOT NULL,
car_co2_km DECIMAL, 
car_energy_label VARCHAR(5), 
car_distance_amount INT,
car_description VARCHAR(255),

CONSTRAINT car_model_fk_fuel
FOREIGN KEY (car_fuel_type)
REFERENCES fuel (car_fuel_type)

);


DROP TABLE IF EXISTS subscription_type;
CREATE TABLE subscription_type (
subscription_type_id INT PRIMARY KEY AUTO_INCREMENT,
subscription_type_name VARCHAR(10) NOT NULL
);

DROP TABLE IF EXISTS car;
CREATE TABLE car (
car_vehicle_number INT PRIMARY KEY AUTO_INCREMENT,
car_chassis_number INT NOT NULL,
car_model_id INT NOT NULL,
car_price_month DECIMAL NOT NULL,
subscription_type_id INT NOT NULL,
car_is_reserved TINYINT NOT NULL,

CONSTRAINT car_fk_car_model
FOREIGN KEY (car_model_id)
REFERENCES car_model (car_model_id),

CONSTRAINT car_fk_subscription_type
FOREIGN KEY (subscription_type_id)
REFERENCES subscription_type (subscription_type_id)
);

DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
customer_id INT PRIMARY KEY AUTO_INCREMENT,
customer_name VARCHAR(30) NOT NULL,
customer_mail VARCHAR(30) NOT NULL,
customer_address VARCHAR(100) NOT NULL,
customer_phone_number VARCHAR(20)
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
employee_id INT PRIMARY KEY AUTO_INCREMENT,
employee_initials VARCHAR(10) NOT NULL,
employee_name VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS location;
CREATE TABLE location (
location_address VARCHAR(50) PRIMARY KEY,
location_phone_number VARCHAR(20) NOT NULL,
location_name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
reservation_id INT PRIMARY KEY AUTO_INCREMENT,
car_vehicle_number INT NOT NULL,
customer_id INT NOT NULL,
location_address VARCHAR(255) NOT NULL,
pickup_date DATE NOT NULL,
return_date DATE NOT NULL,
pickup_time TIME NOT NULL,
return_time TIME NOT NULL,
reservation_payment DECIMAL NOT NULL,
reservation_comment VARCHAR(255),
employee_id INT NOT NULL,

/* foreign key i tabellen reservation - hentes fra tabellerne car, customer, employee og location */

CONSTRAINT reservation_fk_car
FOREIGN KEY (car_vehicle_number)
REFERENCES car (car_vehicle_number),

CONSTRAINT reservation_fk_customer
FOREIGN KEY (customer_id)
REFERENCES customer (customer_id),

CONSTRAINT reservation_fk_employee
FOREIGN KEY (employee_id)
REFERENCES employee (employee_id),

CONSTRAINT reservation_fk_location
FOREIGN KEY (location_address)
REFERENCES location (location_address)

);


DROP TABLE IF EXISTS problem_report;
CREATE TABLE problem_report (
report_id INT PRIMARY KEY AUTO_INCREMENT,
car_vehicle_number INT NOT NULL,
total_price DECIMAL NOT NULL,
employee_id INT NOT NULL,
customer_id INT NOT NULL,


CONSTRAINT problem_report_fk_employee
FOREIGN KEY (employee_id)
REFERENCES employee (employee_id),

CONSTRAINT problem_report_fk_customer
FOREIGN KEY (customer_id)
REFERENCES customer (customer_id),

CONSTRAINT problem_report_fk_car
FOREIGN KEY (car_vehicle_number)
REFERENCES car (car_vehicle_number));



DROP TABLE IF EXISTS problem;
CREATE TABLE problem (
problem_id INT PRIMARY KEY AUTO_INCREMENT,
problem_type varchar(255) NOT NULL,
problem_description varchar(255) NOT NULL,
problem_price DECIMAL NOT NULL,
report_id INT NOT NULL,

CONSTRAINT problem_fk_problem_report
FOREIGN KEY (report_id)
REFERENCES problem_report (report_id)
);
