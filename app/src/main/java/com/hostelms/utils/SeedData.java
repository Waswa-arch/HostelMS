package com.hostelms.utils;

import android.content.Context;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;
import java.util.List;

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

        // Check if we need to upgrade from the old 7-room seed to the new 300-room seed
        List<Room> existingRooms = db.roomDao().getAll();
        if (existingRooms.size() < 100) {
            // If we have very few rooms (like the old seed), clear them to allow fresh 300-room seed
            for (Room r : existingRooms) {
                db.roomDao().delete(r);
            }
            
            String[] hostels = {"Baobab Hall", "Acacia House", "Savanna Block"};
            String[] genders = {"Male", "Female", "Male"};
            double[] prices = {15000, 17000, 13000};
            
            for (int h = 0; h < hostels.length; h++) {
                String hostelName = hostels[h];
                String gender = genders[h];
                double price = prices[h];
                
                for (int i = 1; i <= 100; i++) {
                    Room room = new Room();
                    room.hostelName = hostelName;
                    room.roomNumber = String.valueOf(100 + i);
                    
                    if (i % 3 == 0) {
                        room.roomType = "Single";
                        room.capacity = 1;
                        room.pricePerSemester = price;
                    } else if (i % 3 == 1) {
                        room.roomType = "Double";
                        room.capacity = 2;
                        room.pricePerSemester = price * 0.7;
                    } else {
                        room.roomType = "Quad";
                        room.capacity = 4;
                        room.pricePerSemester = price * 0.5;
                    }
                    
                    room.gender = gender;
                    // ENSURE NO ROOM IS FULL: 
                    // Set occupied to 0 and status to Available
                    room.status = "Available";
                    room.occupied = 0; 

                    room.amenities = "Wi-Fi, Laundry, Security";
                    db.roomDao().insert(room);
                }
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
