DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User1', 'user1@yandex.ru', '{noop}first_user'),
  ('User2', 'user2@mail.ru', '{noop}second_user'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_USER', 100002);

INSERT INTO restaurants (restaurant_name, address) VALUES
  ('Ресторан', 'Одоевского, 1'),
  ('Столовая', 'Коласа, 17'),
  ('Закусочная', 'Козлова, 38');

INSERT INTO dishes (description, cost, restaurant_id, menu_date) VALUES
  ('Суп рассольник', 3.50, 100003,'2018-05-05'),
  ('Гуляш', 3.90, 100003,'2018-05-05'),
  ('Каша картофельная', 2.40, 100004, '2018-05-05'),
  ('Отбивная под сыром', 2.50, 100004, '2018-05-05'),
  ('ХотДог', 1.10, 100005, '2018-05-05'),
  ('Кофе', 0.80, 100005, '2018-05-05'),
  ('Каша гречневая', 2.70, 100003, current_date),
  ('Котлета по киевски', 2.40, 100003, current_date),
  ('Щи', 3.60, 100004, current_date),
  ('Макароны по-флотски', 3.20, 100004, current_date),
  ('Шаурма', 1.20, 100005, current_date),
  ('Чай', 0.50, 100005, current_date);

INSERT INTO votes (restaurant_id, user_id, date) VALUES
  (100003, 100000, '2018-05-05'),
  (100004, 100001, '2018-05-05'),
  (100005, 100001, current_date),
  (100005, 100002, current_date);