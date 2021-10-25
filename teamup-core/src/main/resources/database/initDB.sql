INSERT INTO interests VALUES
(1, 'Like to play football', 'Football'),
(2, 'Like to go fishing', 'Fishing');

INSERT INTO admins VALUES
(1, '20.10.2021 18:00', 'admin@mail.ru', '20.10.2021 18:00', 'Petrov', 'Petrov1', 'Nikolaevich', 'Andrei', '0');

INSERT INTO moderators VALUES
(1, '21.10.2021 18:00', 'modrator@mail.ru', '21.10.2021 18:00', 'Sidorov', 'Sidorov1', 'Ivanovich', 'Nikolai', '1', 10, 11, 12);

INSERT INTO users VALUES
(1, '22.10.2021 18:00', 'user@mail.ru', '22.10.2021 18:00', 'Ivanov', 'Ivanov1', 'Nikolaevich', 'Sergei', '2', 'user Sergei', 32, 'Moscow');

INSERT INTO user_interests VALUES
(1, 1);