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
import com.hostelms.database.entities.Complaint;
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
public final class ComplaintDao_Impl implements ComplaintDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Complaint> __insertionAdapterOfComplaint;

  private final EntityDeletionOrUpdateAdapter<Complaint> __deletionAdapterOfComplaint;

  private final EntityDeletionOrUpdateAdapter<Complaint> __updateAdapterOfComplaint;

  public ComplaintDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfComplaint = new EntityInsertionAdapter<Complaint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `complaints` (`id`,`studentId`,`studentName`,`category`,`subject`,`description`,`priority`,`status`,`adminResponse`,`dateSubmitted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Complaint entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        if (entity.studentName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.studentName);
        }
        if (entity.category == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.category);
        }
        if (entity.subject == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.subject);
        }
        if (entity.description == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.description);
        }
        if (entity.priority == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.priority);
        }
        if (entity.status == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.status);
        }
        if (entity.adminResponse == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.adminResponse);
        }
        statement.bindLong(10, entity.dateSubmitted);
      }
    };
    this.__deletionAdapterOfComplaint = new EntityDeletionOrUpdateAdapter<Complaint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `complaints` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Complaint entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfComplaint = new EntityDeletionOrUpdateAdapter<Complaint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `complaints` SET `id` = ?,`studentId` = ?,`studentName` = ?,`category` = ?,`subject` = ?,`description` = ?,`priority` = ?,`status` = ?,`adminResponse` = ?,`dateSubmitted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Complaint entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        if (entity.studentName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.studentName);
        }
        if (entity.category == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.category);
        }
        if (entity.subject == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.subject);
        }
        if (entity.description == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.description);
        }
        if (entity.priority == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.priority);
        }
        if (entity.status == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.status);
        }
        if (entity.adminResponse == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.adminResponse);
        }
        statement.bindLong(10, entity.dateSubmitted);
        statement.bindLong(11, entity.id);
      }
    };
  }

  @Override
  public long insert(final Complaint c) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfComplaint.insertAndReturnId(c);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Complaint c) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfComplaint.handle(c);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Complaint c) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfComplaint.handle(c);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Complaint> getAll() {
    final String _sql = "SELECT * FROM complaints ORDER BY dateSubmitted DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "studentName");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfAdminResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "adminResponse");
      final int _cursorIndexOfDateSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "dateSubmitted");
      final List<Complaint> _result = new ArrayList<Complaint>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Complaint _item;
        _item = new Complaint();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        if (_cursor.isNull(_cursorIndexOfStudentName)) {
          _item.studentName = null;
        } else {
          _item.studentName = _cursor.getString(_cursorIndexOfStudentName);
        }
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _item.category = null;
        } else {
          _item.category = _cursor.getString(_cursorIndexOfCategory);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        if (_cursor.isNull(_cursorIndexOfPriority)) {
          _item.priority = null;
        } else {
          _item.priority = _cursor.getString(_cursorIndexOfPriority);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfAdminResponse)) {
          _item.adminResponse = null;
        } else {
          _item.adminResponse = _cursor.getString(_cursorIndexOfAdminResponse);
        }
        _item.dateSubmitted = _cursor.getLong(_cursorIndexOfDateSubmitted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Complaint> getByStudent(final int studentId) {
    final String _sql = "SELECT * FROM complaints WHERE studentId = ? ORDER BY dateSubmitted DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "studentName");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfAdminResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "adminResponse");
      final int _cursorIndexOfDateSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "dateSubmitted");
      final List<Complaint> _result = new ArrayList<Complaint>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Complaint _item;
        _item = new Complaint();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        if (_cursor.isNull(_cursorIndexOfStudentName)) {
          _item.studentName = null;
        } else {
          _item.studentName = _cursor.getString(_cursorIndexOfStudentName);
        }
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _item.category = null;
        } else {
          _item.category = _cursor.getString(_cursorIndexOfCategory);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        if (_cursor.isNull(_cursorIndexOfPriority)) {
          _item.priority = null;
        } else {
          _item.priority = _cursor.getString(_cursorIndexOfPriority);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfAdminResponse)) {
          _item.adminResponse = null;
        } else {
          _item.adminResponse = _cursor.getString(_cursorIndexOfAdminResponse);
        }
        _item.dateSubmitted = _cursor.getLong(_cursorIndexOfDateSubmitted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Complaint getById(final int id) {
    final String _sql = "SELECT * FROM complaints WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "studentName");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfAdminResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "adminResponse");
      final int _cursorIndexOfDateSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "dateSubmitted");
      final Complaint _result;
      if (_cursor.moveToFirst()) {
        _result = new Complaint();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        if (_cursor.isNull(_cursorIndexOfStudentName)) {
          _result.studentName = null;
        } else {
          _result.studentName = _cursor.getString(_cursorIndexOfStudentName);
        }
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _result.category = null;
        } else {
          _result.category = _cursor.getString(_cursorIndexOfCategory);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _result.subject = null;
        } else {
          _result.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _result.description = null;
        } else {
          _result.description = _cursor.getString(_cursorIndexOfDescription);
        }
        if (_cursor.isNull(_cursorIndexOfPriority)) {
          _result.priority = null;
        } else {
          _result.priority = _cursor.getString(_cursorIndexOfPriority);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _result.status = null;
        } else {
          _result.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfAdminResponse)) {
          _result.adminResponse = null;
        } else {
          _result.adminResponse = _cursor.getString(_cursorIndexOfAdminResponse);
        }
        _result.dateSubmitted = _cursor.getLong(_cursorIndexOfDateSubmitted);
      } else {
        _result = null;
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
