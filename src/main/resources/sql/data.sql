INSERT INTO price(berthFactor, compartmentFactor, deluxeFactor) VALUE ('0.2', '0.75', '1');
INSERT INTO price(berthFactor, compartmentFactor, deluxeFactor) VALUE ('0.5', '1', '1.5');
INSERT INTO price(berthFactor, compartmentFactor, deluxeFactor) VALUE ('1', '2', '3');

INSERT INTO user(email, password, name, surname, phone, admin) VALUE ("root", "root", "Admin", "Admin", "0", 1);
INSERT INTO user(email, password, name, surname, phone, admin) VALUE ("andy97@ukr.net", "root", "Andrii", "Yashin", "0663533848", 0);

INSERT INTO station(name) VALUE ('Kyiv');
INSERT INTO station(name) VALUE ('Kharkiv');
INSERT INTO station(name) VALUE ('Lviv');
INSERT INTO station(name) VALUE ('Odessa');

INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 12:45:00', '2017-07-01 23:00:00',1, 1,2,300);
INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 14:30:00', '2017-07-01 22:00:00',1, 2,1,300);
INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 23:45:00', '2017-07-02 05:00:00',2, 1,4,500);
INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 11:12:00', '2017-07-01 17:00:00',2, 4,1,500);
INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 06:12:00', '2017-07-01 14:00:00',3, 2,3,800);
INSERT INTO route(fromTime, toTime, priceId, fromId, toId, distance) VALUE ('2017-07-01 05:12:00', '2017-07-01 16:00:00',2, 3,2,800);

INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (1,49,50,50);
INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (2,60,70,20);
INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (3,100,100,100);
INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (4,100,100,100);
INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (5,50,50,50);
INSERT INTO train(routeId, compartmentFree, deluxeFree, berthFree) VALUE (6,30,20,50);

INSERT INTO request(userId, trainId, type, price) VALUE (2,1,'C',60);

