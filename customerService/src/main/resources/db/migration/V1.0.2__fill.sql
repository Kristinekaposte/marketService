INSERT INTO address (phone_number, country, city, postal_code)
VALUES
  ('1234567890', 'USA', 'New York', '10001'),
  ('9876543210', 'UK', 'London', '20001'),
  ('5554443333', 'Canada', 'Toronto', '30001'),
  ('1112223333', 'Australia', 'Sydney', '40001'),
  ('2223334444', 'France', 'Paris', '50001'),
  ('6667778888', 'Germany', 'Berlin', '60001');

INSERT INTO customer (email, password, first_name, last_name, address_id)
VALUES
  ('john.doe@example.com', 'password123', 'John', 'Doe', 1),
  ('jane.smith@example.com', 'pass456word', 'Jane', 'Smith', 2),
  ('bob.johnson@example.com', 'secret123', 'Bob', 'Johnson', 3),
  ('email@email.com', '$2a$12$l.XikxPHVzlUyrbuN/x.oe3BFnW.0BgGiG7bS0TrbKnvA5CWS22wy', 'Emily', 'Jones', 4),
  ('email2@email.com', '$2a$12$cQWoWkI9x8MH8/epQnz49eAJH2SnSXRKuG.zQKAPDgz0NGcQSRNla', 'Alex', 'Wilson', 5),
  ('email3@email.com', '$2a$12$AVkBE0dngFR2zajH7AL4puYWjojIgCnaguMlblhh9Jtq/fm.Yd9N.', 'Sophia', 'Lake', 6);
