
INSERT INTO Account (role, id, active, email, password, username) VALUES
    ('User', 1001, true, 'user@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'user123'),
    ('Administrator', 1002, true, 'admin@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'admin123');

INSERT INTO Type (id, name) VALUES
    (1001, 'Manifestacija');

INSERT INTO Type (id, name) VALUES
    (1002, 'Institucija');

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1001, 'Festival', 1001);

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1002, 'Koncert', 1001);

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1003, 'Podkategorija1', 1002);

INSERT INTO Location (id, address, latitude, longitude) VALUES
    (1001, 'Petrovaradinska tvrdjava', 45.252736, 19.8601);

INSERT INTO Location (id, address, latitude, longitude) VALUES
    (1002, 'Usce', 44.8177044, 20.4284067);

INSERT INTO Location (id, address, latitude, longitude) VALUES
    (1003, 'Bg Arena', 44.8141499, 20.4191007);

INSERT INTO Cultural_offer (id, description, location_id, name, subtype_id) VALUES
    (1001, 'Najveci festival u regionu', 1001, 'Exit festival', 1001);

INSERT INTO Cultural_offer (id, description, location_id, name, subtype_id) VALUES
    (1002, 'Najveci festival u regionu', 1002, 'BeerFest', 1001);

INSERT INTO Cultural_offer (id, description, location_id, name, subtype_id) VALUES
    (1003, 'Najveci festival u regionu',1003 , 'Koncert Zdravko colic', 1002);

INSERT INTO Review (id, comment, rating, user_id, culturaloffer_id) VALUES
    (1001, 'extra', 5, 1001, 1001);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (1001, '2017-01-13T17:09:42.411', 'Najnovija vest o festivalu', 1001);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (1002, '2018-02-14T15:09:42.411', 'Festival je odlozen do daljnjeg', 1001);

INSERT INTO User_culturaloffers (user_id, culturaloffer_id) VALUES
    (1001, 1001);

