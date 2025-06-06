-- Create the database
CREATE DATABASE exam_distribution_test;
GO

USE exam_distribution_test;  
GO

-- exam_distribution_test.staff definition
CREATE TABLE staff (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        account_fe nvarchar(255) NULL,
		account_fpt nvarchar(255) NULL,
		name nvarchar(255) NULL,
		staff_code nvarchar(255) NULL,
		PRIMARY KEY (id)
);
GO

-- exam_distribution_test.facility definition
CREATE TABLE facility (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        code nvarchar(255) NULL,
	name nvarchar(255) NULL,
	PRIMARY KEY (id)
);
GO

-- exam_distribution_test.department definition
CREATE TABLE department (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        code nvarchar(255) NULL,
		name nvarchar(255) NULL,
		PRIMARY KEY (id)
);
GO

-- exam_distribution_test.department_facility definition
CREATE TABLE department_facility (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        id_department uniqueidentifier NULL,
        id_facility uniqueidentifier NULL,
        id_staff uniqueidentifier NULL,
        PRIMARY KEY (id),
		FOREIGN KEY (id_department) REFERENCES department (id),
		FOREIGN KEY (id_facility) REFERENCES facility (id),
		FOREIGN KEY (id_staff) REFERENCES staff (id)
);
GO

-- exam_distribution_test.major definition
CREATE TABLE major (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        name nvarchar(255) NULL,
		code nvarchar(255) NULL,
		PRIMARY KEY (id)
);
GO

-- exam_distribution_test.major_facility definition
CREATE TABLE major_facility (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        id_department_facility uniqueidentifier NULL,
        id_major uniqueidentifier NULL,
        PRIMARY KEY (id),
		FOREIGN KEY (id_department_facility) REFERENCES department_facility (id),
		FOREIGN KEY (id_major) REFERENCES major (id)	
);
GO

-- exam_distribution_test.staff_major_facility definition
CREATE TABLE staff_major_facility (
        status tinyint NULL,
        created_date bigint NULL,
        last_modified_date bigint NULL,
        id uniqueidentifier NOT NULL,
        id_major_facility uniqueidentifier NULL,
        id_staff uniqueidentifier NULL,
        PRIMARY KEY (id),
		FOREIGN KEY (id_major_facility) REFERENCES major_facility (id),
		FOREIGN KEY (id_staff) REFERENCES staff (id)
);
GO

-- Insert data into the staff table
INSERT INTO staff (id, status, created_date, last_modified_date, account_fe, account_fpt, name, staff_code)
VALUES
('550e8400-e29b-41d4-a716-446655440000', 1, 1627849200000, 1627935600000, 'fe_account1@fe.edu.vn', 'fpt_account1@fpt.edu.vn', 'John wick', 'ST001'),
('550e8400-e29b-41d4-a716-446655440001', 1, 1627849200000, 1627935600000, 'fe_account2@fe.edu.vn', 'fpt_account2@fpt.edu.vn', 'Top1 Flo', 'ST002'),
('550e8400-e29b-41d4-a716-446655440002', 1, 1627849200000, 1627935600000, 'fe_account3@fe.edu.vn', 'fpt_account3@fpt.edu.vn', 'Lục Luật', 'ST003');
GO

-- Insert data into the facility table
INSERT INTO facility (id, status, created_date, last_modified_date, code, name)
VALUES
('550e8400-e29b-41d4-a716-446655440010', 1, 1627849200000, 1627935600000, 'FAC001', 'HN'),
('550e8400-e29b-41d4-a716-446655440011', 1, 1627849200000, 1627935600000, 'FAC002', 'HCM');
GO

-- Insert data into the department table
INSERT INTO department (id, status, created_date, last_modified_date, code, name)
VALUES
('550e8400-e29b-41d4-a716-446655440020', 1, 1627849200000, 1627935600000, 'DEP001', 'Department One'),
('550e8400-e29b-41d4-a716-446655440021', 1, 1627849200000, 1627935600000, 'DEP002', 'Department Two'),
('550e8400-e29b-41d4-a716-446655440022', 1, 1627849200000, 1627935600000, 'DEP003', 'Department Three');
GO

-- Insert data into the department_facility table
INSERT INTO department_facility (id, status, created_date, last_modified_date, id_department, id_facility, id_staff)
VALUES
('550e8400-e29b-41d4-a716-446655440030', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440020', '550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440000'),
('550e8400-e29b-41d4-a716-446655440031', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001'),
('550e8400-e29b-41d4-a716-446655440032', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440002');
GO

-- Insert data into the major table
INSERT INTO major (id, status, created_date, last_modified_date, name, code)
VALUES
('550e8400-e29b-41d4-a716-446655440040', 1, 1627849200000, 1627935600000, 'Major One', 'MAJ001'),
('550e8400-e29b-41d4-a716-446655440041', 1, 1627849200000, 1627935600000, 'Major Two', 'MAJ002'),
('550e8400-e29b-41d4-a716-446655440042', 1, 1627849200000, 1627935600000, 'Major Three', 'MAJ003');
GO

-- Insert data into the major_facility table
INSERT INTO major_facility (id, status, created_date, last_modified_date, id_department_facility, id_major)
VALUES
('550e8400-e29b-41d4-a716-446655440050', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440030', '550e8400-e29b-41d4-a716-446655440040'),
('550e8400-e29b-41d4-a716-446655440051', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440031', '550e8400-e29b-41d4-a716-446655440041'),
('550e8400-e29b-41d4-a716-446655440052', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440032', '550e8400-e29b-41d4-a716-446655440042');
GO

-- Insert data into the staff_major_facility table
INSERT INTO staff_major_facility (id, status, created_date, last_modified_date, id_major_facility, id_staff)
VALUES
('550e8400-e29b-41d4-a716-446655440060', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440050', '550e8400-e29b-41d4-a716-446655440000'),
('550e8400-e29b-41d4-a716-446655440061', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440051', '550e8400-e29b-41d4-a716-446655440001'),
('550e8400-e29b-41d4-a716-446655440062', 1, 1627849200000, 1627935600000, '550e8400-e29b-41d4-a716-446655440052', '550e8400-e29b-41d4-a716-446655440002');
GO

select * from facility f
join department_facility df
on df.id_facility = f.id

select * from department
select * from major
select * from major_facility


select f.name, d.name, m.name from staff_major_facility smf
join staff s on smf.id_staff = s.id
join major_facility mf on smf.id_major_facility = mf.id
join major m on mf.id_major = m.id
join department_facility dmf on mf.id_department_facility = dmf.id
join facility f on dmf.id_facility = f.id
join department d on dmf.id_department = d.id
where s.id = '550e8400-e29b-41d4-a716-446655440000'

select f.name, d.name, m.name from staff_major_facility smf
join staff s on smf.id_staff = s.id
join major_facility mf on smf.id_major_facility = mf.id
join major m on mf.id_major = m.id
join department_facility dmf on mf.id_department_facility = dmf.id
join facility f on dmf.id_facility = f.id
join department d on dmf.id_department = d.id
where s.id = ?1


SELECT df.id_facility
FROM staff_major_facility smf
JOIN major_facility mf ON smf.id_major_facility = mf.id
JOIN department_facility df ON mf.id_department_facility = df.id
WHERE smf.id_staff = '550e8400-e29b-41d4-a716-446655440000'

select * from major_facility


select smf.id as 'id staff major facility' from staff_major_facility smf
where smf.id_staff = '550E8400-E29B-41D4-A716-446655440002' and smf.id_major_facility = '550E8400-E29B-41D4-A716-446655440051' 


select major.id from major where name = 'Major Three'

select dmf.id from department_facility dmf where dmf.id_facility = '550E8400-E29B-41D4-A716-446655440010'
and dmf.id_department = '550E8400-E29B-41D4-A716-446655440020'
and dmf.id_staff = '550E8400-E29B-41D4-A716-446655440000'

select * from staff
select * from facility
select * from major
select * from department