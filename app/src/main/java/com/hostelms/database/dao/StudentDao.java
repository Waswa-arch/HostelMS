package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Student;
import java.util.List;
@Dao
public interface StudentDao {
    @Insert long insert(Student s);
    @Update void update(Student s);
    @Delete void delete(Student s);
    @Query("SELECT * FROM students WHERE role = 'student' ORDER BY name ASC")
    List<Student> getAllStudents();
    @Query("SELECT * FROM students WHERE id = :id")
    Student getById(int id);
    @Query("SELECT * FROM students WHERE email = :email AND password = :password LIMIT 1")
    Student login(String email, String password);
    @Query("SELECT * FROM students WHERE regNumber = :regNo LIMIT 1")
    Student getByRegNumber(String regNo);
    @Query("SELECT * FROM students WHERE roomId = :roomId")
    List<Student> getStudentsByRoom(int roomId);
    @Query("SELECT * FROM students WHERE email = :email LIMIT 1")
    Student getByEmail(String email);
    @Query("UPDATE students SET roomId = :roomId, bedNumber = :bed WHERE id = :studentId")
    void assignRoom(int studentId, int roomId, int bed);
}
