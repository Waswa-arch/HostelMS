-- HostelMS database schema
-- Run this once in phpMyAdmin or MySQL CLI

CREATE DATABASE IF NOT EXISTS hostelms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hostelms;

CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(60) UNIQUE NOT NULL,
    email VARCHAR(120),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    reg_number VARCHAR(60) UNIQUE NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(120) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(30),
    course VARCHAR(100),
    age INT DEFAULT 0,
    room_id INT DEFAULT NULL,
    status ENUM('active','inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hostels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    person_in_charge VARCHAR(100),
    contact_number VARCHAR(40),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_id INT NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    type VARCHAR(50),
    price DECIMAL(10,2),
    amenities TEXT,
    capacity INT DEFAULT 1,
    occupied INT DEFAULT 0,
    status ENUM('available','occupied','maintenance') DEFAULT 'available',
    gender VARCHAR(20) DEFAULT 'Mixed',
    FOREIGN KEY (hostel_id) REFERENCES hostels(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    room_id INT NOT NULL,
    hostel_id INT NOT NULL,
    check_in DATE,
    check_out DATE,
    meal_bundle VARCHAR(80) DEFAULT 'Room Only',
    total_price DECIMAL(10,2),
    status ENUM('pending','confirmed','cancelled','completed') DEFAULT 'confirmed',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);

CREATE TABLE IF NOT EXISTS complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    category VARCHAR(80),
    subject VARCHAR(200),
    description TEXT NOT NULL,
    priority VARCHAR(20) DEFAULT 'Normal',
    status ENUM('Open','In Progress','Resolved') DEFAULT 'Open',
    admin_response TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE IF NOT EXISTS announcements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    is_urgent TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

-- Default admin (password: admin123)
INSERT IGNORE INTO admins (name, username, email, password)
VALUES ('Administrator', 'admin', 'admin@hostelms.ac',
        '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi');

-- Sample hostels
INSERT IGNORE INTO hostels (id, name, description, person_in_charge, contact_number, image_url)
VALUES
(1, 'Baobab Hall',  'Male hostel with modern facilities',  'Mr. Kamau',   '+254 700 111 222', 'https://example.com/baobab.jpg'),
(2, 'Acacia House', 'Female hostel with garden and Wi-Fi', 'Ms. Wanjiru',  '+254 700 333 444', 'https://example.com/acacia.jpg'),
(3, 'Savanna Block','Mixed hostel close to main campus',   'Mr. Otieno',  '+254 700 555 666', 'https://example.com/savanna.jpg');

INSERT IGNORE INTO rooms (hostel_id, room_number, type, price, amenities, capacity, status, gender)
VALUES
(1,'101','Single',1500,'Wi-Fi, Wardrobe, Fan',1,'available','Male'),
(1,'102','Double',1000,'Wi-Fi, Wardrobe',2,'available','Male'),
(2,'201','Single',1700,'Wi-Fi, AC, Wardrobe',1,'available','Female'),
(3,'301','Single',1300,'Wi-Fi, Fan',1,'available','Mixed');

-- ── Room checklist (added for post-booking room inspection) ────────────────
CREATE TABLE IF NOT EXISTS room_checklists (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    student_id     INT NOT NULL,
    booking_id     INT NOT NULL UNIQUE,
    items_checked  INT DEFAULT 0,
    total_items    INT DEFAULT 0,
    summary        TEXT,
    submitted_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);
