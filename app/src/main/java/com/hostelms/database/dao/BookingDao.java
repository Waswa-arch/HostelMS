package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Booking;
import java.util.List;
@Dao
public interface BookingDao {
    @Insert long insert(Booking b);
    @Update void update(Booking b);
    @Delete void delete(Booking b);
    @Query("SELECT * FROM bookings ORDER BY bookingDate DESC")
    List<Booking> getAll();
    @Query("SELECT * FROM bookings WHERE studentId = :sid ORDER BY bookingDate DESC")
    List<Booking> getByStudent(int sid);
    @Query("SELECT * FROM bookings WHERE id = :id")
    Booking getById(int id);
}
