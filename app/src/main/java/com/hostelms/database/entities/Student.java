package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name, gender, course, regNumber, email, password, phone, role, profilePhotoPath;
    public int age, roomId, bedNumber;
    public long admissionDate;
    public double amountOwed;
    public String hostelName, roomNumber, roomType; // denormalised for display
}
