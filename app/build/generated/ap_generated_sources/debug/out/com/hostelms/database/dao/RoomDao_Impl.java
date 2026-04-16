package com.hostelms.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.hostelms.database.entities.Room;
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
public final class RoomDao_Impl implements RoomDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Room> __insertionAdapterOfRoom;

  private final EntityDeletionOrUpdateAdapter<Room> __deletionAdapterOfRoom;

  private final EntityDeletionOrUpdateAdapter<Room> __updateAdapterOfRoom;

  private final SharedSQLiteStatement __preparedStmtOfIncrementOccupied;

  private final SharedSQLiteStatement __preparedStmtOfDecrementOccupied;

  public RoomDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoom = new EntityInsertionAdapter<Room>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `rooms` (`id`,`hostelName`,`roomNumber`,`roomType`,`amenities`,`status`,`gender`,`capacity`,`occupied`,`pricePerSemester`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Room entity) {
        statement.bindLong(1, entity.id);
        if (entity.hostelName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.roomType);
        }
        if (entity.amenities == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.amenities);
        }
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        if (entity.gender == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.gender);
        }
        statement.bindLong(8, entity.capacity);
        statement.bindLong(9, entity.occupied);
        statement.bindDouble(10, entity.pricePerSemester);
      }
    };
    this.__deletionAdapterOfRoom = new EntityDeletionOrUpdateAdapter<Room>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `rooms` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Room entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfRoom = new EntityDeletionOrUpdateAdapter<Room>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `rooms` SET `id` = ?,`hostelName` = ?,`roomNumber` = ?,`roomType` = ?,`amenities` = ?,`status` = ?,`gender` = ?,`capacity` = ?,`occupied` = ?,`pricePerSemester` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Room entity) {
        statement.bindLong(1, entity.id);
        if (entity.hostelName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.roomType);
        }
        if (entity.amenities == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.amenities);
        }
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        if (entity.gender == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.gender);
        }
        statement.bindLong(8, entity.capacity);
        statement.bindLong(9, entity.occupied);
        statement.bindDouble(10, entity.pricePerSemester);
        statement.bindLong(11, entity.id);
      }
    };
    this.__preparedStmtOfIncrementOccupied = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE rooms SET occupied = occupied + 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDecrementOccupied = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE rooms SET occupied = occupied - 1 WHERE id = ? AND occupied > 0";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Room r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfRoom.insertAndReturnId(r);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Room r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfRoom.handle(r);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Room r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRoom.handle(r);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void incrementOccupied(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementOccupied.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementOccupied.release(_stmt);
    }
  }

  @Override
  public void decrementOccupied(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDecrementOccupied.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDecrementOccupied.release(_stmt);
    }
  }

  @Override
  public List<Room> getAll() {
    final String _sql = "SELECT * FROM rooms ORDER BY hostelName, roomNumber";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfAmenities = CursorUtil.getColumnIndexOrThrow(_cursor, "amenities");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
      final int _cursorIndexOfOccupied = CursorUtil.getColumnIndexOrThrow(_cursor, "occupied");
      final int _cursorIndexOfPricePerSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerSemester");
      final List<Room> _result = new ArrayList<Room>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Room _item;
        _item = new Room();
        _item.id = _cursor.getInt(_cursorIndexOfId);
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
        if (_cursor.isNull(_cursorIndexOfAmenities)) {
          _item.amenities = null;
        } else {
          _item.amenities = _cursor.getString(_cursorIndexOfAmenities);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _item.gender = null;
        } else {
          _item.gender = _cursor.getString(_cursorIndexOfGender);
        }
        _item.capacity = _cursor.getInt(_cursorIndexOfCapacity);
        _item.occupied = _cursor.getInt(_cursorIndexOfOccupied);
        _item.pricePerSemester = _cursor.getDouble(_cursorIndexOfPricePerSemester);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Room getById(final int id) {
    final String _sql = "SELECT * FROM rooms WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfAmenities = CursorUtil.getColumnIndexOrThrow(_cursor, "amenities");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
      final int _cursorIndexOfOccupied = CursorUtil.getColumnIndexOrThrow(_cursor, "occupied");
      final int _cursorIndexOfPricePerSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerSemester");
      final Room _result;
      if (_cursor.moveToFirst()) {
        _result = new Room();
        _result.id = _cursor.getInt(_cursorIndexOfId);
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
        if (_cursor.isNull(_cursorIndexOfAmenities)) {
          _result.amenities = null;
        } else {
          _result.amenities = _cursor.getString(_cursorIndexOfAmenities);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _result.status = null;
        } else {
          _result.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _result.gender = null;
        } else {
          _result.gender = _cursor.getString(_cursorIndexOfGender);
        }
        _result.capacity = _cursor.getInt(_cursorIndexOfCapacity);
        _result.occupied = _cursor.getInt(_cursorIndexOfOccupied);
        _result.pricePerSemester = _cursor.getDouble(_cursorIndexOfPricePerSemester);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Room> getByHostel(final String hostel) {
    final String _sql = "SELECT * FROM rooms WHERE hostelName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hostel == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hostel);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfAmenities = CursorUtil.getColumnIndexOrThrow(_cursor, "amenities");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
      final int _cursorIndexOfOccupied = CursorUtil.getColumnIndexOrThrow(_cursor, "occupied");
      final int _cursorIndexOfPricePerSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerSemester");
      final List<Room> _result = new ArrayList<Room>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Room _item;
        _item = new Room();
        _item.id = _cursor.getInt(_cursorIndexOfId);
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
        if (_cursor.isNull(_cursorIndexOfAmenities)) {
          _item.amenities = null;
        } else {
          _item.amenities = _cursor.getString(_cursorIndexOfAmenities);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _item.gender = null;
        } else {
          _item.gender = _cursor.getString(_cursorIndexOfGender);
        }
        _item.capacity = _cursor.getInt(_cursorIndexOfCapacity);
        _item.occupied = _cursor.getInt(_cursorIndexOfOccupied);
        _item.pricePerSemester = _cursor.getDouble(_cursorIndexOfPricePerSemester);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Room> getAvailable() {
    final String _sql = "SELECT * FROM rooms WHERE occupied < capacity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final int _cursorIndexOfAmenities = CursorUtil.getColumnIndexOrThrow(_cursor, "amenities");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
      final int _cursorIndexOfOccupied = CursorUtil.getColumnIndexOrThrow(_cursor, "occupied");
      final int _cursorIndexOfPricePerSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerSemester");
      final List<Room> _result = new ArrayList<Room>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Room _item;
        _item = new Room();
        _item.id = _cursor.getInt(_cursorIndexOfId);
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
        if (_cursor.isNull(_cursorIndexOfAmenities)) {
          _item.amenities = null;
        } else {
          _item.amenities = _cursor.getString(_cursorIndexOfAmenities);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _item.gender = null;
        } else {
          _item.gender = _cursor.getString(_cursorIndexOfGender);
        }
        _item.capacity = _cursor.getInt(_cursorIndexOfCapacity);
        _item.occupied = _cursor.getInt(_cursorIndexOfOccupied);
        _item.pricePerSemester = _cursor.getDouble(_cursorIndexOfPricePerSemester);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getAllHostels() {
    final String _sql = "SELECT DISTINCT hostelName FROM rooms";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
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
