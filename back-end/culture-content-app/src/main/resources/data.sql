INSERT INTO Account (role, id, active, email, password, username) VALUES
    ('User', 1, true, 'user@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'user123'),
    ('Administrator', 2, true, 'admin@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'admin123');

INSERT INTO Type (id, name) VALUES
    (1, 'Manifestacija');

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1, 'Festival', 1);

INSERT INTO Cultural_offer (id, description, location, name, subtype_id) VALUES
    (1, 'Najveci festival u regionu', 'Petrovaradinska tvrdjava', 'Exit festival', 1);

INSERT INTO Review (id, comment, rating, user_id, culturaloffer_id) VALUES
    (1, 'extra', 5, 1, 1);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (1, '2017-01-13T17:09:42.411', 'Najnovija vest o festivalu', 1);