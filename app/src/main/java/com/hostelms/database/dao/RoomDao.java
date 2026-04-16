package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Room;
import java.util.List;
@Dao
public interface RoomDao {
    @Insert long insert(Room r);
    @Update void update(Room r);
    @Delete void delete(Room r);
    @Query("SELECT * FROM rooms ORDER BY hostelName, roomNumber")
    List<Room> getAll();
    @Query("SELECT * FROM rooms WHERE id = :id")
    Room getById(int id);
    @Query("SELECT * FROM rooms WHERE hostelName = :hostel")
    List<Room> getByHostel(String hostel);
    @Query("SELECT * FROM rooms WHERE occupied < capacity")
    List<Room> getAvailable();
    @Query("SELECT DISTINCT hostelName FROM rooms")
    List<String> getAllHostels();
    @Query("UPDATE rooms SET occupied = occupied + 1 WHERE id = :id")
    void incrementOccupied(int id);
    @Query("UPDATE rooms SET occupied = occupied - 1 WHERE id = :id AND occupied > 0")
    void decrementOccupied(int id);
}
