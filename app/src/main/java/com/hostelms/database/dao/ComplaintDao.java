package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Complaint;
import java.util.List;
@Dao
public interface ComplaintDao {
    @Insert long insert(Complaint c);
    @Update void update(Complaint c);
    @Delete void delete(Complaint c);
    @Query("SELECT * FROM complaints ORDER BY dateSubmitted DESC")
    List<Complaint> getAll();
    @Query("SELECT * FROM complaints WHERE studentId = :studentId ORDER BY dateSubmitted DESC")
    List<Complaint> getByStudent(int studentId);
    @Query("SELECT * FROM complaints WHERE id = :id")
    Complaint getById(int id);
}
