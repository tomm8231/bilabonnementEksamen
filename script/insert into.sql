
/* INSERT INTO (forskellige tabeller) */

INSERT INTO fuel (car_fuel_type, distance_unit) 
VALUES ('Benzin','km/l'),
('Elbil','km pr opladning'),
('Diesel','km/l'), 
('Plug-In Hybrid', 'km/l');

INSERT INTO subscription_type(subscription_type_name)
VALUES ('LIMITED'),('UNLIMITED');

 
INSERT INTO location(location_address, location_phone_number, location_name)
VALUES ('Brydehusvej 18, 2750 Ballerup ', '70133040', 'FDM Ballerup'),
('Slotsherrensvej 114c 2650 Rødovre', '89885080','Bilabonnement HQ'),
('Vintervej 1, Hasle, 8210 Århus', '21221469', 'FDMshop Midtjylland'),
('Virumgårdsvej 4, 8, 2830 Virum', '45858866', 'DS Virum');



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
('Mette Skov', 'skov@mgmail.com', 'Hellerupvej 50, 2900 Hellerup', '87364536');
 

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
/* 10 */('Opel', 'Corsa Edition+ ML', 100, 'Benzin', 'Manuelt', 123, 'A+', 18.2, 'Kommer bl.a. med Apple CarPlay, baksensor og 7" touchskærm'),
/* 11 */ ('Citroën', 'C5 Aircross Prestige ML', 225, 'Plug-In Hybrid', 'Automatgear', 32, NULL, 71.4, 'Kommer bl.a. med 3D-navigation, bakkamera og Adaptiv fartpilot'),
/* 12 */ ('Citroën', 'C3 Aircross Feel ML', 110, 'Benzin', 'Manuelt', 133, 'A+', 16.9, 'Kommer bl.a. med sædevarme, Apple CarPlay og varme i forsæderne'),
/* 13 */ ('Peugeot', '208 Active ML', 75, 'Benzin', 'Manuelt', 122, 'A+', 18.9, 'Kommer bl.a. med 15" fælge, LED kørelys og fartpilot m. fartbegrænser'),
/* 14 */('Peugeot', '2008 Active Pack ML', 130, 'Benzin', 'Manuelt', 127, 'A+', 17.9, 'Kommer med Bakkamera, Adaptiv fartpilot og Apple CarPlay.'),
/* 15 */ ('Peugeot', '2008 Allure Pack ML', 130, 'Benzin', 'Manuelt', 136, 'A+', 17.9, 'Kommer bl.a. med Adaptiv fartpilot, aut. klimaanlæg og p-sensor'),
/* 16 */ ('Peugeot', '3008 GT ML', 130, 'Benzin', 'Automatgear', 136, 'A+', 15.4, 'Kommer bl.a. med Driver Assistance Pack, 3D navigation med 10" skærm og Full LED forlygter');

INSERT INTO car (car_chassis_number, car_model_id, car_price_month, subscription_type_id, car_is_reserved)
VALUES 
/* */
/*1*/ (2343242, 1, 2699, 2, 0),
/*2*/ (4694321, 2, 2799, 2, 0),
/*3*/(6843967, 3, 2899, 2, 0),
/*4*/(1456075, 4, 2999, 2, 0),

/*5*/(6014582, 5, 4499, 1, 0), /*MINI LEASE*/
/*6*/(24324324, 6, 5499, 1, 0), /*MINI LEASE*/
/*7*/(4444444, 7, 2999, 2, 0),
/*8*/(5555555,8, 3499, 1, 0), /*MINI LEASE*/
/*9*/(6666666, 9, 3199, 1, 0), /*MINI LEASE*/
/*10*/(9467351, 10, 2999, 1, 0),
/*11*/(7894561, 11, 5499, 1, 0),
/*12*/(6549317, 12, 3100, 1, 0),
/*13*/(8945126, 13, 3499, 1, 0),
/*14*/(3265487, 14, 3899, 1, 0),
/*15*/(7946255, 15, 4299, 1, 0),
/*16*/(2323547, 16, 5999, 1, 0);

