package com.hostelms.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "announcements")
public class Announcement {
    @PrimaryKey(autoGenerate = true) public int id;
    public String title, body, author;
    public boolean isUrgent;
    public long datePosted;
}
