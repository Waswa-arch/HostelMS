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
import com.hostelms.database.entities.Booking;
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
public final class BookingDao_Impl implements BookingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Booking> __insertionAdapterOfBooking;

  private final EntityDeletionOrUpdateAdapter<Booking> __deletionAdapterOfBooking;

  private final EntityDeletionOrUpdateAdapter<Booking> __updateAdapterOfBooking;

  public BookingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBooking = new EntityInsertionAdapter<Booking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bookings` (`id`,`studentId`,`roomId`,`hostelName`,`roomNumber`,`roomType`,`mealBundle`,`status`,`totalPrice`,`bookingDate`,`checkInDone`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Booking entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        statement.bindLong(3, entity.roomId);
        if (entity.hostelName == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.roomType);
        }
        if (entity.mealBundle == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.mealBundle);
        }
        if (entity.status == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.status);
        }
        statement.bindDouble(9, entity.totalPrice);
        statement.bindLong(10, entity.bookingDate);
        final int _tmp = entity.checkInDone ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__deletionAdapterOfBooking = new EntityDeletionOrUpdateAdapter<Booking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bookings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Booking entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfBooking = new EntityDeletionOrUpdateAdapter<Booking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bookings` SET `id` = ?,`studentId` = ?,`roomId` = ?,`hostelName` = ?,`roomNumber` = ?,`roomType` = ?,`mealBundle` = ?,`status` = ?,`totalPrice` = ?,`bookingDate` = ?,`checkInDone` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Booking entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        statement.bindLong(3, entity.roomId);
        if (entity.hostelName == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.roomType);
        }
        if (entity.mealBundle == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.mealBundle);
        }
        if (entity.status == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.status);
        }
        statement.bindDouble(9, entity.totalPrice);
        statement.bindLong(10, entity.bookingDate);
        final int _tmp = entity.checkInDone ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.id);
      }
    };
  }

  @Override
  public long insert(final Booking b) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfBooking.insertAndReturnId(b);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Booking b) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBooking.handle(b);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Booking b) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBooking.handle(b);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Booking> getAll() {
    final String _sql = "SELECT * FROM bookings ORDER BY bookingDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfMealBundle = CursorUtil.getColumnIndexOrThrow(_cursor, "mealBundle");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfBookingDate = CursorUtil.getColumnIndexOrThrow(_cursor, "bookingDate");
      final int _cursorIndexOfCheckInDone = CursorUtil.getColumnIndexOrThrow(_cursor, "checkInDone");
      final List<Booking> _result = new ArrayList<Booking>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Booking _item;
        _item = new Booking();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _item.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        if (_cursor.isNull(_cursorIndexOfHostelName)) {
          _item.hostelName = null;
        } else {
          _item.hostelName = _cursor.getString(_cursorIndexOfHostelName);
        }
        if (_cursor.isNull(_cursorIndexOfRoomNumber)) {
          _item.roomNumber = null;
        } else {
          _item.roomNumber = _cursor.getString(_cursorIndexOfRoomNumber);
        }
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _item.roomType = null;
        } else {
          _item.roomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        if (_cursor.isNull(_cursorIndexOfMealBundle)) {
          _item.mealBundle = null;
        } else {
          _item.mealBundle = _cursor.getString(_cursorIndexOfMealBundle);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.totalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
        _item.bookingDate = _cursor.getLong(_cursorIndexOfBookingDate);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCheckInDone);
        _item.checkInDone = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Booking> getByStudent(final int sid) {
    final String _sql = "SELECT * FROM bookings WHERE studentId = ? ORDER BY bookingDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sid);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfMealBundle = CursorUtil.getColumnIndexOrThrow(_cursor, "mealBundle");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfBookingDate = CursorUtil.getColumnIndexOrThrow(_cursor, "bookingDate");
      final int _cursorIndexOfCheckInDone = CursorUtil.getColumnIndexOrThrow(_cursor, "checkInDone");
      final List<Booking> _result = new ArrayList<Booking>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Booking _item;
        _item = new Booking();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _item.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        if (_cursor.isNull(_cursorIndexOfHostelName)) {
          _item.hostelName = null;
        } else {
          _item.hostelName = _cursor.getString(_cursorIndexOfHostelName);
        }
        if (_cursor.isNull(_cursorIndexOfRoomNumber)) {
          _item.roomNumber = null;
        } else {
          _item.roomNumber = _cursor.getString(_cursorIndexOfRoomNumber);
        }
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _item.roomType = null;
        } else {
          _item.roomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        if (_cursor.isNull(_cursorIndexOfMealBundle)) {
          _item.mealBundle = null;
        } else {
          _item.mealBundle = _cursor.getString(_cursorIndexOfMealBundle);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.totalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
        _item.bookingDate = _cursor.getLong(_cursorIndexOfBookingDate);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCheckInDone);
        _item.checkInDone = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Booking getById(final int id) {
    final String _sql = "SELECT * FROM bookings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfMealBundle = CursorUtil.getColumnIndexOrThrow(_cursor, "mealBundle");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
      final int _cursorIndexOfBookingDate = CursorUtil.getColumnIndexOrThrow(_cursor, "bookingDate");
      final int _cursorIndexOfCheckInDone = CursorUtil.getColumnIndexOrThrow(_cursor, "checkInDone");
      final Booking _result;
      if (_cursor.moveToFirst()) {
        _result = new Booking();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _result.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        if (_cursor.isNull(_cursorIndexOfHostelName)) {
          _result.hostelName = null;
        } else {
          _result.hostelName = _cursor.getString(_cursorIndexOfHostelName);
        }
        if (_cursor.isNull(_cursorIndexOfRoomNumber)) {
          _result.roomNumber = null;
        } else {
          _result.roomNumber = _cursor.getString(_cursorIndexOfRoomNumber);
        }
        if (_cursor.isNull(_cursorIndexOfRoomType)) {
          _result.roomType = null;
        } else {
          _result.roomType = _cursor.getString(_cursorIndexOfRoomType);
        }
        if (_cursor.isNull(_cursorIndexOfMealBundle)) {
          _result.mealBundle = null;
        } else {
          _result.mealBundle = _cursor.getString(_cursorIndexOfMealBundle);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _result.status = null;
        } else {
          _result.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.totalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
        _result.bookingDate = _cursor.getLong(_cursorIndexOfBookingDate);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCheckInDone);
        _result.checkInDone = _tmp != 0;
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
