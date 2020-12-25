
INSERT INTO Account (role, id, active, email, password, username) VALUES
    ('User', 1001, true, 'user@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'user123'),
    ('Administrator', 1002, true, 'admin@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'admin123');

INSERT INTO Type (id, name) VALUES
    (1001, 'Manifestacija');

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1001, 'Festival', 1001);

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1002, 'Koncert', 1001);

INSERT INTO Cultural_offer (id, description, location, name, subtype_id) VALUES
    (1001, 'Najveci festival u regionu', 'Petrovaradinska tvrdjava', 'Exit festival', 1001);

INSERT INTO Cultural_offer (id, description, location, name, subtype_id) VALUES
    (1002, 'Najveci festival u regionu', 'Usce', 'BeerFest', 1001);

INSERT INTO Cultural_offer (id, description, location, name, subtype_id) VALUES
    (1003, 'Najveci festival u regionu', 'Petrovaradinska tvrdjava', 'Koncert Zdravko colic', 1002);

INSERT INTO Review (id, comment, rating, user_id, culturaloffer_id) VALUES
    (1001, 'extra', 5, 1001, 1001);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (1001, '2017-01-13T17:09:42.411', 'Najnovija vest o festivalu', 1001);

