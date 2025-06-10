-- Wstawianie adresów (muszą być pierwsze, bo są referencowane)
INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES 
    (1, 'ul. Glowna 10', 'mieszkanie 5', 'Warszawa', '00-001'),
    (2, 'ul. Kwiatowa 25', NULL, 'Krakow', '30-200'),
    (3, 'ul. Sloneczna 8', 'budynek A', 'Wroclaw', '50-300'),
    (4, 'ul. Parkowa 15', NULL, 'Gdansk', '80-400');

-- Wstawianie lekarzy
INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
VALUES 
    (1, 'Jan', 'Kowalski', '123456789', 'jan.kowalski@hospital.pl', 'DOC001', 'GP', 1),
    (2, 'Anna', 'Nowak', '987654321', 'anna.nowak@hospital.pl', 'DOC002', 'SURGEON', 2),
    (3, 'Piotr', 'Wisniewski', '555123456', 'piotr.wisniewski@hospital.pl', 'DOC003', 'DERMATOLOGIST', 3);

-- Wstawianie pacjentów
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, age, address_id, version)
VALUES 
    (1, 'Maria', 'Kowalczyk', '111222333', 'maria.kowalczyk@email.pl', 'PAT001', '1985-03-15', 38, 1, 0),
    (2, 'Tomasz', 'Jankowski', '444555666', 'tomasz.jankowski@email.pl', 'PAT002', '1990-07-22', 33, 2, 0),
    (3, 'Katarzyna', 'Zielinska', '777888999', NULL, 'PAT003', '1978-11-30', 45, 4, 0),
    (4, 'Marcin', 'Lewandowski', '222333444', 'marcin.lewandowski@email.pl', 'PAT004', '1992-05-18', 31, 3, 0);

-- Wstawianie zabiegów medycznych
INSERT INTO medical_treatment (id, description, type)
VALUES 
    (1, 'Badanie USG jamy brzusznej', 'USG'),
    (2, 'EKG spoczynkowe', 'EKG'),
    (3, 'Zdjecie RTG klatki piersiowej', 'RTG'),
    (4, 'USG tarczycy', 'USG'),
    (5, 'EKG wysilkowe', 'EKG');

-- Wstawianie wizyt
INSERT INTO visit (id, description, time, patient_id, doctor_id)
VALUES 
    (1, 'Kontrola ogolna', '2023-10-15 10:00:00', 1, 1),
    (2, 'Badanie specjalistyczne', '2023-10-16 14:30:00', 1, 2),
    (3, 'Wizyta kontrolna', '2023-10-17 09:15:00', 2, 1),
    (4, 'Badanie dermatologiczne', '2023-10-18 11:00:00', 3, 3),
    (5, 'Konsultacja kardiologiczna', '2023-10-19 16:00:00', 2, 1),
    (6, 'Badanie okresowe', '2023-10-20 13:30:00', 4, 2),
    (7, 'Druga wizyta pacjenta 1', '2023-11-15 10:30:00', 1, 3),
    (8, 'Trzecia wizyta pacjenta 1', '2023-12-10 09:00:00', 1, 1);

-- Wstawianie połączeń wizyt z zabiegami (tabela łącząca)
INSERT INTO visit_treatment (visit_id, treatment_id)
VALUES 
    (1, 2), -- Wizyta 1: EKG spoczynkowe
    (2, 1), -- Wizyta 2: USG jamy brzusznej
    (2, 3), -- Wizyta 2: RTG klatki piersiowej
    (3, 2), -- Wizyta 3: EKG spoczynkowe
    (4, 4), -- Wizyta 4: USG tarczycy
    (5, 2), -- Wizyta 5: EKG spoczynkowe
    (5, 5), -- Wizyta 5: EKG wysiłkowe
    (6, 1), -- Wizyta 6: USG jamy brzusznej
    (7, 4), -- Wizyta 7: USG tarczycy
    (8, 2); -- Wizyta 8: EKG spoczynkowe

-- Znajdz pacjentow po nazwisku
SELECT last_name FROM patient

-- Znajdz wszystkie wizyty pacjenta po jego ID
SELECT V.*, P.FIRST_NAME, P.LAST_NAME FROM VISIT V JOIN PATIENT P ON V.PATIENT_ID = P.ID WHERE P.ID = 1

-- Znajdz pacjentow ktorzy mieli wiecej niz X wizyt (X jest parametrem wejsciowym)
SELECT P.ID, P.FIRST_NAME, P.LAST_NAME, COUNT(V.ID) AS VISIT_COUNT FROM PATIENT P JOIN VISIT V ON P.ID = V.PATIENT_ID GROUP BY P.ID, P.FIRST_NAME, P.LAST_NAME HAVING COUNT(V.ID) > 3

-- Znajdz pacjentow po dodanym przez Ciebie polu - nie wyszukuj wprost po wartosci, uzyj zapytania typu wieksze/mniejsze/pozniej/wczesniej/zawiera, w zaleznosci od wybranego typu zmiennej.
SELECT * FROM PATIENT WHERE DATE_OF_BIRTH < '1990-01-01'