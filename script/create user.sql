USE car_leasing;
DROP USER IF EXISTS car_leasing_user@localhost;
CREATE USER car_leasing_user@localhost IDENTIFIED BY '1234';
GRANT ALL
ON car_leasing.*
TO car_leasing_user@localhost;
SELECT User, HOST FROM mysql.user;
SHOW GRANTS FOR car_leasing_user@localhost;
