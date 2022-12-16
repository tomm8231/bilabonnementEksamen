
/* INSERT INTO (forskellige tabeller) */

INSERT INTO fuel (car_fuel_type, distance_unit)
VALUES ('Benzin','km/l'),
       ('Elbil','km pr opladning'),
       ('Diesel','km/l'),
       ('Plug-In Hybrid', 'km/l');

INSERT INTO subscription_type(subscription_type_name)
VALUES ('LIMITED'),('UNLIMITED');


INSERT INTO location(location_address, location_phone_number, location_name)
VALUES
/* nr 1*/('Brydehusvej 18, 2750 Ballerup', '70133040', 'FDM Ballerup'),
/* nr 2*/('Slotsherrensvej 114c 2650 Rødovre', '89885080','Bilabonnement HQ'),
/* nr 3*/('Vintervej 1, Hasle, 8210 Århus', '21221469', 'FDMshop Midtjylland'),
/* nr 4*/('Virumgårdsvej 4, 8, 2830 Virum', '45858866', 'DS Virum');



INSERT INTO employee(employee_initials, employee_name)
VALUES
    ('AA', 'Anders Andersen'),
    ('BB', 'Bille Bo'),
    ('CC', 'Cecilie Gulliksen'),
    ('JH', 'Jonas Hansen'),
    ('RO', 'Rasmus Olsen');


INSERT INTO customer(customer_name, customer_mail, customer_address, customer_phone_number)
VALUES
    ('Mikael Willumsen', 'willum@gmail.com', 'Hestevej 20, 6584 Hvalsø', '23435343'),
    ('Mogens Jensen', 'mogens@sol.dk', 'Nørrebrogade 55, 2200 Nørrebro', '98673512'),
    ('Mette Skov', 'skov@mgmail.com', 'Hellerupvej 50, 2900 Hellerup', '87364536'),
    ('Hans Toft', 'toft@mgmail.com', 'Hellerupvej 56, 2900 Hellerup', '87364536'),
    ('Sebastia Hjulman', 'sebhjul@mgmail.com', 'Rentevej 10, 2100', '12345678'),
    ('Heidi mus', 'skov@mgmail.com', 'Tirsdagvænget 23, 3400 Hillerød', '87654321'),
    ('Fin Bahn', 'fin@mgmail.com', 'Odinsvej 30, 3400 Hillerød', '11223344'),
    ('Trine Hovmand', 'tri@mgmail.com', 'Sophienborg Alle 2A, 3400 Hillerød', '55667788'),
    ('Mark Lark', 'markl@mgmail.com', 'Brennum Park 20M, 3400 Hillerød', '15879658'),
    ('Rikke Møller', 'rim@mgmail.com', 'Ellevej 6, 3450 Allerød', '32653698'),
    ('Ulla Hus', 'jen@mgmail.com', 'Prunusvej 25, 3450 Allerød', '78458956'),
    ('Gorm Denim', 'gorm@mgmail.com', 'Søageren 17, 3450 Allerød', '45687624'),
    ('Paula Pill', 'pill@mgmail.com', 'Højdevej 22, 3540 Lynge', '48756823'),
    ('Niels Nielsen', 'niel@mgmail.com', 'Hesselvangen 5, 3660 Stenløse', '45897531'),
    ('Verner Holm', 'holm@mgmail.com', 'Vestre Alle 46, 3500 Værløse', '25658951'),
    ('Sten Haard', 'haard@mgmail.com', 'Hirsevangen 5, 2750 Ballerup', '25658951');


/*INSERT cars into database*/
INSERT INTO car_model (car_brand, car_model, car_hp, car_fuel_type, car_gearbox_type, car_co2_km, car_energy_label, car_distance_amount, car_description)
VALUES
/* nr 1*/ ('Citroën', 'C1 Le Mans', 72, 'Benzin', 'Manuelt', 109, 'A+', 20.8, 'Kommer bl.a. med Apple CarPlay, 7" Touchskærm og Bakkamera'),
/* nr 2*/ ('Peugeot', '108 Active+', 72, 'Benzin', 'Manuelt', 111, 'A+', 20.4, 'Kommer bl.a. med Apple CarPlay, 7" touchskærm og læderrat'),
/* nr 4*/ ('Fiat', '500e CABRIO Icon Pack', 118, 'Elbil', 'Automatgear', 0, NULL, 311, 'Kommer blandt andet med bakkamerra, el-sidespejle og nøglefri start'),
/* nr 7 */ ('Fiat', '500e Icon Pack', 118, 'Elbil', 'Automatgear', 0, NULL, 311, 'Kommer bl.a. med bakkamera, el-sidespejle og nøglefri start'),
/* nr 8 */ ('Citroën', 'C3 Le Mans', 83, 'Benzin', 'Manuelt', 123, 'A+', 18.5, 'Kommer med Apple CarPlay, 7" touchskærm og fartpilot'),
          ('Peugeot', '208 Active+', 100, 'Benzin', 'Manuelt', 125, 'A+', 18.9, 'Kommer bl.a. Apple Carplay, LED lørelys, læderrat, klimaanlæg og fartpilot'),
          ('Peugeot', 'e-2008 GT Line', 136, 'Elbil', 'Automatgear', 0, NULL, 310, 'Kommer bl.a. med 7" touchskærm, adaptiv fartpilot og bakkamera'),

/*MINI LEASE-biler */
/* nr 3 */ ('Peugeot', '208 Envy ML', 82, 'Benzin', 'Manuelt', 109, 'A+', 23.4,'Kommer bl.a. med Apple CarPlay, 2-zonet klima og fartpilot'),
/* nr 5*/ ('Peugeot', '2008 Allure Pack ML', 130, 'Benzin', 'Automatgear', 136, 'A', 16.7, 'Kommer med Bakkamera, Adaptiv fartpilot og Apple CarPlay.'),
/* nr 6*/ ('DS', '4 Performance LinePack ML', 130, 'Diesel', 'Automatgear', 127, 'A+', 20.8, 'Kommer bl.a. med Apple CarPlay, flush dørhåndtag og 10" touchskærm'),
/* nr 9 */ ('Opel', 'Crossland GS Line ML', 110, 'Benzin', 'Manuelt', 131, 'A+', 16.4, 'Kommer bl.a. med aut. klimaanlæg m. 2 zoner, Apple CarPlay og HiFi-anlægg'),
/* nr 10 */('Opel', 'Corsa Edition+ ML', 100, 'Benzin', 'Manuelt', 123, 'A+', 18.2, 'Kommer bl.a. med Apple CarPlay, baksensor og 7" touchskærm'),
/* nr 11 */ ('Citroën', 'C5 Aircross Prestige ML', 225, 'Plug-In Hybrid', 'Automatgear', 32, NULL, 71.4, 'Kommer bl.a. med 3D-navigation, bakkamera og Adaptiv fartpilot'),
/* nr 12 */ ('Citroën', 'C3 Aircross Feel ML', 110, 'Benzin', 'Manuelt', 133, 'A+', 16.9, 'Kommer bl.a. med sædevarme, Apple CarPlay og varme i forsæderne'),
/* nr 13 */ ('Peugeot', '208 Active ML', 75, 'Benzin', 'Manuelt', 122, 'A+', 18.9, 'Kommer bl.a. med 15" fælge, LED kørelys og fartpilot m. fartbegrænser'),
/* nr 14 */('Peugeot', '2008 Active Pack ML', 130, 'Benzin', 'Manuelt', 127, 'A+', 17.9, 'Kommer med Bakkamera, Adaptiv fartpilot og Apple CarPlay.'),
/* nr 15 */ ('Peugeot', '2008 Allure Pack ML', 130, 'Benzin', 'Manuelt', 136, 'A+', 17.9, 'Kommer bl.a. med Adaptiv fartpilot, aut. klimaanlæg og p-sensor'),
/* nr 16 */ ('Peugeot', '3008 GT ML', 130, 'Benzin', 'Automatgear', 136, 'A+', 15.4, 'Kommer bl.a. med Driver Assistance Pack, 3D navigation med 10" skærm og Full LED forlygter'),



INSERT INTO car (car_chassis_number, car_model_id, car_price_month, subscription_type_id, car_is_reserved)
VALUES
/* */
/*01*/(2343242, 1, 2699, 2, 0),
/*02*/(4694321, 2, 2799, 2, 0),
/*03*/(6843967, 3, 2899, 2, 0),
/*04*/(1456075, 4, 2999, 2, 0),
/*05*/(6014582, 5, 4499, 1, 0), /*MINI LEASE*/
/*06*/(2432432, 6, 5499, 1, 0), /*MINI LEASE*/
/*07*/(4444444, 7, 2999, 2, 0),
/*08*/(5555555, 8, 3499, 1, 0), /*MINI LEASE*/
/*09*/(6666666, 9, 3199, 1, 0), /*MINI LEASE*/
/*10*/(6666548, 10, 2999, 1, 0),
/*11*/(1121564, 11, 5499, 1, 0),
/*12*/(9876456, 12, 3100, 1, 0),
/*13*/(4597566, 13, 3499, 1, 0),
/*14*/(1231564, 14, 3899, 1, 0),
/*15*/(1231616, 15, 4299, 1, 0),
/*16*/(2312165, 16, 5999, 1, 0),
/*17*/(8794564, 5, 4499, 1, 0), /*MINI LEASE*/
/*18*/(2131564, 6, 5499, 1, 0), /*MINI LEASE*/
/*19*/(4598798, 7, 2999, 2, 0),
/*20*/(1235654, 8, 3499, 1, 0), /*MINI LEASE*/
/*21*/(2131531, 9, 3199, 1, 0), /*MINI LEASE*/
/*22*/(2313456, 10, 2999, 1, 0),
/*23*/(5464949, 11, 5499, 1, 0),
/*24*/(4894864, 12, 3100, 1, 0),
/*25*/(2135486, 13, 3499, 1, 0),
/*26*/(3215648, 14, 3899, 1, 0),
/*27*/(4879756, 15, 4299, 1, 0),
/*28*/(1231564, 16, 5999, 1, 0);

insert into reservation (car_vehicle_number, customer_id, location_address, pickup_date, return_date, pickup_time, return_time, reservation_payment, reservation_comment, employee_id)
values
    (1,1,'Brydehusvej 18, 2750 Ballerup', '2021-01-01', '2022-04-15', '12:00:00', '12:00:00', 10000, 'Kan blive forsinket', 1),
    (2,2,'Brydehusvej 18, 2750 Ballerup', '2021-02-05', '2022-05-15', '12:00:00', '12:00:00', 12000, 'Ikke vasket', 1),
    (3,3,'Brydehusvej 18, 2750 Ballerup', '2021-03-12', '2022-09-15', '12:00:00', '12:00:00', 11000, 'Mudret', 2),
    (4,4,'Brydehusvej 18, 2750 Ballerup', '2021-05-20', '2022-12-15', '12:00:00', '12:00:00', 15000, 'Svag lugt', 2),
    (5,5,'Slotsherrensvej 114c 2650 Rødovre', '2022-01-04', '2022-06-15', '10:00:00', '12:00:00', 12000, 'Svag lugt', 4),
    (6,6,'Slotsherrensvej 114c 2650 Rødovre', '2022-03-07', '2022-08-12', '10:00:00', '12:00:00', 14000, 'Svag lugt', 4),
    (7,7,'Slotsherrensvej 114c 2650 Rødovre', '2022-06-23', '2022-11-13', '10:00:00', '12:00:00', 17000, 'Ingen kommentarer', 3),
    (8,8,'Slotsherrensvej 114c 2650 Rødovre', '2022-08-28', '2022-12-29', '10:00:00', '12:00:00', 16000, 'Ingen kommentarer', 3),
    (9,9,'Slotsherrensvej 114c 2650 Rødovre', '2022-07-15', '2023-01-03', '10:00:00', '12:00:00', 13000, 'Ingen kommentarer', 1),
    (10,10,'Slotsherrensvej 114c 2650 Rødovre', '2022-11-17', '2023-02-15', '10:00:00', '12:00:00', 12000, 'Ingen kommentarer', 2),
    (11,11,'Slotsherrensvej 114c 2650 Rødovre', '2023-01-18', '2023-11-18', '10:00:00', '12:00:00', 11000, 'Ingen kommentarer', 3),
    (12,12,'Virumgårdsvej 4, 8, 2830 Virum', '2023-01-02', '2023-04-01', '09:00:00', '12:00:00', 14000, 'Ingen kommentarer', 5),
    (13,13,'Virumgårdsvej 4, 8, 2830 Virum', '2023-02-04', '2023-06-04', '09:00:00', '12:00:00', 10000, 'Ingen kommentarer', 5),
    (14,14,'Virumgårdsvej 4, 8, 2830 Virum', '2023-03-09', '2023-07-07', '09:00:00', '12:00:00', 10000, 'Ingen kommentarer', 5),
    (15,15,'Virumgårdsvej 4, 8, 2830 Virum', '2023-01-15', '2023-06-09', '09:00:00', '12:00:00', 13000, 'Ingen kommentarer', 3),
    (16,16,'Virumgårdsvej 4, 8, 2830 Virum', '2023-02-27', '2023-08-14', '09:00:00', '12:00:00', 15000, 'Ingen kommentarer', 2);









