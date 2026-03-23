package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "attendance")
public class Attendance {
    @PrimaryKey(autoGenerate = true) public int id;
    public int studentId;
    public String studentName, location, type;
    public long timestamp;
}
