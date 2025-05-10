1.
select last_name from patient

2.
select * from visit where PATIENT_ID = 5;

3.
select p.* from patient p join visit v on p.id = v.patient_id group by p.id having count(v.id) > 0

4.
select * from patient where email like '%lewa%'