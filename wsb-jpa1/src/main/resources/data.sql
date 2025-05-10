INSERT INTO Address (city, address_Line1, address_Line2, postal_Code) VALUES
                                                                          ('Opole', 'ul. Polna 3', 'lok. 21', '45-101'),
                                                                          ('Rzeszów', 'ul. Lipowa 7', NULL, '35-202'),
                                                                          ('Sopot', 'ul. Plażowa 12', 'Apt. 2', '81-303'),
                                                                          ('Białystok', 'ul. Kręta 18', NULL, '15-404'),
                                                                          ('Toruń', 'ul. Jasna 9', NULL, '87-505'),
                                                                          ('Kielce', 'ul. Wesoła 4', 'lok. 5', '25-606'),
                                                                          ('Gliwice', 'ul. Leśna 1', NULL, '44-707'),
                                                                          ('Olsztyn', 'ul. Ogrodowa 10', NULL, '10-808'),
                                                                          ('Zielona Góra', 'ul. Kwiatowa 11', NULL, '65-909'),
                                                                          ('Gorzów Wielkopolski', 'ul. Wschodnia 2', 'lok. 7', '66-010');


INSERT INTO doctor (first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES
                                                                                                                   ('Andrzej', 'Janowski', '123456789', 'andrzej.janowski@example.com', 'D001', 'GP', 1),
                                                                                                                   ('Piotr', 'Nowak', '234567890', 'piotr.nowak@example.com', 'D002', 'OCULIST', 2),
                                                                                                                   ('Kacper', 'Lewandowski', '345678901', 'kacper.lewandowski@example.com', 'D003', 'SURGEON', 3),
                                                                                                                   ('Maria', 'Dąbrowska', '456789012', 'maria.dabrowska@example.com', 'D004', 'DERMATOLOGIST', 4),
                                                                                                                   ('Tomasz', 'Lewandowski', '567890123', 'tomasz.lewandowski@example.com', 'D005', 'GP', 5),
                                                                                                                   ('Ewa', 'Zielińska', '678901234', 'ewa.zielinska@example.com', 'D006', 'OCULIST', 6),
                                                                                                                   ('Bartosz', 'Wilk', '789012345', 'bartosz.wilk@example.com', 'D007', 'SURGEON', 7),
                                                                                                                   ('Alicja', 'Kamińska', '890123456', 'alicja.kaminska@example.com', 'D008', 'DERMATOLOGIST', 8),
                                                                                                                   ('Paweł', 'Jankowski', '901234567', 'pawel.jankowski@example.com', 'D009', 'GP', 9),
                                                                                                                   ('Piotr', 'Mazur', '012345678', 'piotr.mazur@example.com', 'D010', 'OCULIST', 10);



INSERT INTO Patient (first_Name, last_Name, telephone_Number, email, patient_Number, date_Of_Birth, address_id) VALUES
                                                                                                              ('Jan', 'Kowalski', '111111111', 'jan.kowalski@example.com', 'P001', '1975-02-11', 1),
                                                                                                              ('Barbara', 'Dąbrowska', '222222222', 'barbara.dabrowska@example.com', 'P002', '1991-12-02', 2),
                                                                                                              ('Cezary', 'Lewandowski', '333333333', 'cezary.lewandowski@example.com', 'P003', '1988-01-15', 3),
                                                                                                              ('Dorota', 'Nowak', '444444444', 'dorota.nowak@example.com', 'P004', '2000-02-28', 4),
                                                                                                              ('Daria', 'Lewandowska', '555555555', 'daria.lewandowska@example.com', 'P005', '1974-06-30', 5),
                                                                                                              ('Filip', 'Zalewski', '666666666', 'filip.zalewski@example.com', 'P006', '1989-10-21', 6),
                                                                                                              ('Gabriela', 'Wilk', '777777777', 'gabriela.wilk@example.com', 'P007', '1992-01-11', 7),
                                                                                                              ('Grzegorz', 'Gocha', '888888888', 'grzegorz.gocha@example.com', 'P008', '1966-12-14', 8),
                                                                                                              ('Ignacy', 'Jankowski', '999999999', 'ignacy.jankowski@example.com', 'P009', '1977-05-18', 9),
                                                                                                              ('Jolanta', 'Mazur', '000000000', 'jolanta.mazur@example.com', 'P010', '1999-12-17', 10);


INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES
                                                                 ('Konsultacja kardiologiczna', '2024-04-01 09:00:00', 1, 1),
                                                                 ('Badanie EEG', '2024-04-02 10:30:00', 2, 2),
                                                                 ('Operacja wyrostka', '2024-04-03 08:45:00', 3, 3),
                                                                 ('Wizyta dermatologiczna', '2024-04-04 13:00:00', 4, 4),
                                                                 ('Przegląd ogólny', '2024-04-05 15:20:00', 5, 5),
                                                                 ('Badanie wzroku', '2024-04-06 07:50:00', 6, 6),
                                                                 ('Konsultacja chirurgiczna', '2024-04-07 11:40:00', 7, 7),
                                                                 ('Wstępna diagnoza skórna', '2024-04-08 12:30:00', 8, 8),
                                                                 ('Terapia grupowa', '2024-04-09 14:10:00', 9, 9),
                                                                 ('Badanie okulistyczne', '2024-04-10 16:00:00', 10, 10);


INSERT INTO Medical_Treatment (description, type, visit_id) VALUES
                                                                ('Test wysiłkowy', 'EKG', 1),
                                                                ('Tomografia głowy', 'RTG', 2),
                                                                ('USG brzucha', 'USG', 3),
                                                                ('Szczepionka HPV', 'USG', 4),
                                                                ('Leczenie alergii', 'USG', 5),
                                                                ('Laserowe usunięcie znamienia', 'USG', 6),
                                                                ('Test tarczycy', 'USG', 7),
                                                                ('Biopsja skóry', 'USG', 8),
                                                                ('Psychoterapia grupowa', 'USG', 9),
                                                                ('Badanie ciśnienia oka', 'USG', 10);
