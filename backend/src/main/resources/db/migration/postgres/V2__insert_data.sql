INSERT INTO work.garage(name,address,phone_number) VALUES
('Garage A','123 Main St','555-1234'), ('Garage B','456 Elm St','555-5678');

insert into work.opening_time(day_of_week, start_time, end_time, garage_id) values
('MONDAY', '08:00:00', '17:00:00',1),
('TUESDAY', '08:00:00', '17:00:00',1),
('WEDNESDAY', '08:00:00', '12:00:00',1),
('THURSDAY', '08:00:00', '17:00:00',1),
('FRIDAY', '08:00:00', '17:00:00',1),
('MONDAY', '09:00:00', '18:00:00',2),
('TUESDAY', '09:00:00', '18:00:00',2),
('WEDNESDAY', '09:00:00', '12:00:00',2),
('THURSDAY', '09:00:00', '18:00:00',2),
('FRIDAY', '09:00:00', '18:00:00',2);
