package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "rooms")
public class Room {
    @PrimaryKey(autoGenerate = true) public int id;
    public String hostelName, roomNumber, roomType, amenities, status, gender;
    public int capacity, occupied;
    public double pricePerSemester;
}
