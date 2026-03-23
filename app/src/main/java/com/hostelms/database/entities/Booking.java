package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "bookings")
public class Booking {
    @PrimaryKey(autoGenerate = true) public int id;
    public int studentId, roomId;
    public String hostelName, roomNumber, roomType, mealBundle, status;
    public double totalPrice;
    public long bookingDate;
    public boolean checkInDone;
}
