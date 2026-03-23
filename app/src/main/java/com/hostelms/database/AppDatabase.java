package com.hostelms.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hostelms.database.dao.*;
import com.hostelms.database.entities.*;

@Database(
    entities = {
        Student.class, 
        com.hostelms.database.entities.Room.class, 
        Complaint.class, 
        Announcement.class, 
        Attendance.class, 
        Booking.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract StudentDao studentDao();
    public abstract RoomDao roomDao();
    public abstract ComplaintDao complaintDao();
    public abstract AnnouncementDao announcementDao();
    public abstract AttendanceDao attendanceDao();
    public abstract BookingDao bookingDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hostelms.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
