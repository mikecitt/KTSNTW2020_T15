INSERT INTO Account (role, id, active, email, password, username) VALUES
    ('User', 1, true, 'user1@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'user1234'),
    ('Administrator', 2, true, 'admin1@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'admin1234'),
    ('User', 3, false, 'notactive1@example.com', '$2a$10$AYTmLNGhVJiX9GKIjgzQ5.yYzFDcspoxrIOfEDuqaHo1SHKcyyAGm', 'notactive1');

INSERT INTO Verification_token (id, expiry_date, token, account_id) VALUES
    (1, '2021-12-27T10:58:55.042', 'da9110a4-985d-4ce2-b514-e0abf7272eb8', 3),
    (2, '2019-12-27T10:58:55.042', 'da9110a4-985d-4ce2-b514-e04562472eb4', 3);

INSERT INTO Type (id, name) VALUES
    (1, 'Manifestacija1');

INSERT INTO Type (id, name) VALUES
    (2, 'Institucija1');

INSERT INTO Sub_type (id, name, type_id) VALUES
    (1, 'Festival1', 1);

INSERT INTO Sub_type (id, name, type_id) VALUES
    (2, 'Festival1111', 1);

INSERT INTO Location (id, address, latitude, longitude) VALUES
    (1, 'Petrovaradinska tvrdjava', 45.252736, 19.8601);

INSERT INTO Location (id, address, latitude, longitude) VALUES
    (2, 'Usce', 44.8177044, 20.4284067);

INSERT INTO Cultural_offer (id, description, location_id, name, subtype_id) VALUES
    (1, 'Najveci festival u regionu', 1, 'Exit festival1', 1);

INSERT INTO Cultural_offer (id, description, location_id, name, subtype_id) VALUES
    (2, 'Najveci festival na mediteranu', 2, 'Sea Dance festival1', 1);

INSERT INTO Review (comment, rating, user_id, culturaloffer_id) VALUES
    ('extra', 5, 1, 1);

INSERT INTO Review (comment, rating, user_id, culturaloffer_id) VALUES
    ('fantastic', 4, 1, 1);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (1, '2017-01-13T17:09:42.411', 'Najnovija vest o festivalu', 1);

INSERT INTO News (id, date, text, culturaloffer_id) VALUES
    (2, '2017-01-13T17:09:42.411', 'Jos novija vest o festivalu', 1);

INSERT INTO User_culturaloffers (user_id, culturaloffer_id) VALUES
    (1, 1);
