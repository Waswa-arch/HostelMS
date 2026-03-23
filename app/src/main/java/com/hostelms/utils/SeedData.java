package com.hostelms.utils;

import android.content.Context;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;

public class SeedData {
    public static void seedIfEmpty(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);

        // Seed admin
        if (db.studentDao().getByEmail("admin@hostelms.ac") == null) {
            Student admin = new Student();
            admin.name = "Administrator"; admin.email = "admin@hostelms.ac";
            admin.password = "admin123"; admin.role = "admin";
            admin.regNumber = "ADMIN001"; admin.age = 30; admin.gender = "Male";
            admin.course = "Administration"; admin.phone = "+254700000000";
            admin.admissionDate = System.currentTimeMillis();
            db.studentDao().insert(admin);
        }

        // Seed rooms
        if (db.roomDao().getAll().isEmpty()) {
            String[][] rooms = {
                {"Baobab Hall","101","Single","1","15000","Male","Available"},
                {"Baobab Hall","102","Double","2","10000","Male","Available"},
                {"Baobab Hall","103","Quad","4","7500","Mixed","Available"},
                {"Acacia House","201","Single","1","17000","Female","Available"},
                {"Acacia House","202","Double","2","12000","Female","Available"},
                {"Savanna Block","301","Single","1","13000","Male","Available"},
                {"Savanna Block","302","Quad","4","7000","Male","Available"},
            };
            for (String[] r : rooms) {
                Room room = new Room();
                room.hostelName = r[0]; room.roomNumber = r[1]; room.roomType = r[2];
                room.capacity = Integer.parseInt(r[3]); room.pricePerSemester = Double.parseDouble(r[4]);
                room.gender = r[5]; room.status = r[6]; room.occupied = 0;
                room.amenities = "Wi-Fi, Laundry, Security";
                db.roomDao().insert(room);
            }
        }

        // Seed announcement
        if (db.announcementDao().getAll().isEmpty()) {
            Announcement a = new Announcement();
            a.title = "Welcome to HostelMS!";
            a.body = "This platform helps you manage your hostel experience. Book rooms, scan QR codes, raise complaints, and stay updated with announcements.";
            a.author = "Administration"; a.isUrgent = false;
            a.datePosted = System.currentTimeMillis();
            db.announcementDao().insert(a);
        }
    }
}
