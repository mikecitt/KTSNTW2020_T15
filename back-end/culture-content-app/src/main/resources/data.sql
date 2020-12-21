INSERT INTO Account (role,  active, email, password, username) VALUES
    ('User',  true, 'user@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'user123'),
    ('Administrator',  true, 'admin@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'admin123');

INSERT INTO Type ( name) VALUES
    ('Manifestacija');

INSERT INTO Sub_type ( name, type_id) VALUES
    ('Festival', 1);

INSERT INTO Cultural_offer ( description, location, name, subtype_id) VALUES
    ('Najveci festival u regionu', 'Petrovaradinska tvrdjava', 'Exit festival', 1);

INSERT INTO Review ( comment, rating, user_id, culturaloffer_id) VALUES
    ('extra', 5, 1, 1);

INSERT INTO News ( date, text, culturaloffer_id) VALUES
    ( '2017-01-13T17:09:42.411', 'Najnovija vest o festivalu', 1);