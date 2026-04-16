package com.hostelms.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.hostelms.database.entities.Announcement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AnnouncementDao_Impl implements AnnouncementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Announcement> __insertionAdapterOfAnnouncement;

  private final EntityDeletionOrUpdateAdapter<Announcement> __deletionAdapterOfAnnouncement;

  private final EntityDeletionOrUpdateAdapter<Announcement> __updateAdapterOfAnnouncement;

  public AnnouncementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnnouncement = new EntityInsertionAdapter<Announcement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `announcements` (`id`,`title`,`body`,`author`,`isUrgent`,`datePosted`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Announcement entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.body == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.body);
        }
        if (entity.author == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.author);
        }
        final int _tmp = entity.isUrgent ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.datePosted);
      }
    };
    this.__deletionAdapterOfAnnouncement = new EntityDeletionOrUpdateAdapter<Announcement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `announcements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Announcement entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfAnnouncement = new EntityDeletionOrUpdateAdapter<Announcement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `announcements` SET `id` = ?,`title` = ?,`body` = ?,`author` = ?,`isUrgent` = ?,`datePosted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Announcement entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.body == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.body);
        }
        if (entity.author == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.author);
        }
        final int _tmp = entity.isUrgent ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.datePosted);
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final Announcement a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfAnnouncement.insertAndReturnId(a);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Announcement a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAnnouncement.handle(a);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Announcement a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAnnouncement.handle(a);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Announcement> getAll() {
    final String _sql = "SELECT * FROM announcements ORDER BY datePosted DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
      final int _cursorIndexOfIsUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "isUrgent");
      final int _cursorIndexOfDatePosted = CursorUtil.getColumnIndexOrThrow(_cursor, "datePosted");
      final List<Announcement> _result = new ArrayList<Announcement>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Announcement _item;
        _item = new Announcement();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _item.body = null;
        } else {
          _item.body = _cursor.getString(_cursorIndexOfBody);
        }
        if (_cursor.isNull(_cursorIndexOfAuthor)) {
          _item.author = null;
        } else {
          _item.author = _cursor.getString(_cursorIndexOfAuthor);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsUrgent);
        _item.isUrgent = _tmp != 0;
        _item.datePosted = _cursor.getLong(_cursorIndexOfDatePosted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
