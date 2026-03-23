package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "complaints")
public class Complaint {
    @PrimaryKey(autoGenerate = true) public int id;
    public int studentId;
    public String studentName, category, subject, description, priority, status, adminResponse;
    public long dateSubmitted;
}
