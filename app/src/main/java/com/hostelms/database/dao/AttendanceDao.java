package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Attendance;
import java.util.List;
@Dao
public interface AttendanceDao {
    @Insert long insert(Attendance a);
    @Query("SELECT * FROM attendance ORDER BY timestamp DESC")
    List<Attendance> getAll();
    @Query("SELECT * FROM attendance WHERE studentId = :sid ORDER BY timestamp DESC")
    List<Attendance> getByStudent(int sid);
    @Query("SELECT * FROM attendance WHERE timestamp BETWEEN :from AND :to ORDER BY timestamp DESC")
    List<Attendance> getByDateRange(long from, long to);
    @Query("SELECT * FROM attendance WHERE location = :location ORDER BY timestamp DESC")
    List<Attendance> getByLocation(String location);
    @Query("SELECT * FROM attendance WHERE studentId = :sid AND timestamp BETWEEN :from AND :to")
    List<Attendance> filterByStudentAndDate(int sid, long from, long to);
}
