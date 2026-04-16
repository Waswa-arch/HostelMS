package com.hostelms.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class StudentNotification {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int     studentId;
    public String  title;
    public String  body;
    public String  type;       // "ROOM_ALLOCATED", "ANNOUNCEMENT", "COMPLAINT_UPDATE"
    public boolean isRead;
    public long    timestamp;
}
