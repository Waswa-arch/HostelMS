package com.hostelms.database.dao;
import androidx.room.*;
import com.hostelms.database.entities.Announcement;
import java.util.List;
@Dao
public interface AnnouncementDao {
    @Insert long insert(Announcement a);
    @Update void update(Announcement a);
    @Delete void delete(Announcement a);
    @Query("SELECT * FROM announcements ORDER BY datePosted DESC")
    List<Announcement> getAll();
}
