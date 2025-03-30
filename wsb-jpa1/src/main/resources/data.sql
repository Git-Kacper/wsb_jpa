-- ADDRESS
insert into address (id, address_line1, address_line2, city, postal_code)
values
    (901, 'ul. Główna 10', 'm.36', 'Warszawa', '00-001'),
    (902, 'ul. Długa 5', 'm.12', 'Kraków', '31-002');

-- PATIENT
insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth)
values
    (201, 'Jan', 'Kowalski', '123456789', 'jan.kowalski@example.com', 'P123', '1990-05-10'),
    (202, 'Anna', 'Nowak', '987654321', 'anna.nowak@example.com', 'P456', '1985-12-01');

