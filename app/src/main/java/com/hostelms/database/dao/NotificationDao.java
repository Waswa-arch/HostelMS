package com.hostelms.database.dao;

import androidx.room.*;
import com.hostelms.database.entities.StudentNotification;
import java.util.List;

@Dao
public interface NotificationDao {
    @Insert
    long insert(StudentNotification n);

    @Query("SELECT * FROM notifications WHERE studentId = :sid ORDER BY timestamp DESC")
    List<StudentNotification> getForStudent(int sid);

    @Query("SELECT COUNT(*) FROM notifications WHERE studentId = :sid AND isRead = 0")
    int getUnreadCount(int sid);

    @Query("UPDATE notifications SET isRead = 1 WHERE studentId = :sid")
    void markAllRead(int sid);

    @Query("DELETE FROM notifications WHERE id = :id")
    void delete(int id);
}
