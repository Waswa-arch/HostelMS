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
import com.hostelms.database.entities.Student;
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
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  private final SharedSQLiteStatement __preparedStmtOfAssignRoom;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `students` (`id`,`name`,`gender`,`course`,`regNumber`,`email`,`password`,`phone`,`role`,`profilePhotoPath`,`age`,`roomId`,`bedNumber`,`admissionDate`,`amountOwed`,`hostelName`,`roomNumber`,`roomType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.gender == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.gender);
        }
        if (entity.course == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.course);
        }
        if (entity.regNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.regNumber);
        }
        if (entity.email == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.email);
        }
        if (entity.password == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.password);
        }
        if (entity.phone == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.phone);
        }
        if (entity.role == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.role);
        }
        if (entity.profilePhotoPath == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.profilePhotoPath);
        }
        statement.bindLong(11, entity.age);
        statement.bindLong(12, entity.roomId);
        statement.bindLong(13, entity.bedNumber);
        statement.bindLong(14, entity.admissionDate);
        statement.bindDouble(15, entity.amountOwed);
        if (entity.hostelName == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.roomType);
        }
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `students` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`name` = ?,`gender` = ?,`course` = ?,`regNumber` = ?,`email` = ?,`password` = ?,`phone` = ?,`role` = ?,`profilePhotoPath` = ?,`age` = ?,`roomId` = ?,`bedNumber` = ?,`admissionDate` = ?,`amountOwed` = ?,`hostelName` = ?,`roomNumber` = ?,`roomType` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.gender == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.gender);
        }
        if (entity.course == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.course);
        }
        if (entity.regNumber == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.regNumber);
        }
        if (entity.email == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.email);
        }
        if (entity.password == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.password);
        }
        if (entity.phone == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.phone);
        }
        if (entity.role == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.role);
        }
        if (entity.profilePhotoPath == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.profilePhotoPath);
        }
        statement.bindLong(11, entity.age);
        statement.bindLong(12, entity.roomId);
        statement.bindLong(13, entity.bedNumber);
        statement.bindLong(14, entity.admissionDate);
        statement.bindDouble(15, entity.amountOwed);
        if (entity.hostelName == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.hostelName);
        }
        if (entity.roomNumber == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.roomNumber);
        }
        if (entity.roomType == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.roomType);
        }
        statement.bindLong(19, entity.id);
      }
    };
    this.__preparedStmtOfAssignRoom = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE students SET roomId=?, bedNumber=?, hostelName=?, roomNumber=?, roomType=? WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Student s) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfStudent.insertAndReturnId(s);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Student s) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfStudent.handle(s);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Student s) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfStudent.handle(s);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void assignRoom(final int studentId, final int roomId, final int bed,
      final String hostelName, final String roomNumber, final String roomType) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAssignRoom.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, roomId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, bed);
    _argIndex = 3;
    if (hostelName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, hostelName);
    }
    _argIndex = 4;
    if (roomNumber == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, roomNumber);
    }
    _argIndex = 5;
    if (roomType == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, roomType);
    }
    _argIndex = 6;
    _stmt.bindLong(_argIndex, studentId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfAssignRoom.release(_stmt);
    }
  }

  @Override
  public List<Student> getAllStudents() {
    final String _sql = "SELECT * FROM students WHERE role='student' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
      final int _cursorIndexOfRegNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "regNumber");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfProfilePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePhotoPath");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfBedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "bedNumber");
      final int _cursorIndexOfAdmissionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "admissionDate");
      final int _cursorIndexOfAmountOwed = CursorUtil.getColumnIndexOrThrow(_cursor, "amountOwed");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Student _item;
        _item = new Student();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _item.gender = null;
        } else {
          _item.gender = _cursor.getString(_cursorIndexOfGender);
        }
        if (_cursor.isNull(_cursorIndexOfCourse)) {
          _item.course = null;
        } else {
          _item.course = _cursor.getString(_cursorIndexOfCourse);
        }
        if (_cursor.isNull(_cursorIndexOfRegNumber)) {
          _item.regNumber = null;
        } else {
          _item.regNumber = _cursor.getString(_cursorIndexOfRegNumber);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _item.email = null;
        } else {
          _item.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _item.password = null;
        } else {
          _item.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _item.phone = null;
        } else {
          _item.phone = _cursor.getString(_cursorIndexOfPhone);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _item.role = null;
        } else {
          _item.role = _cursor.getString(_cursorIndexOfRole);
        }
        if (_cursor.isNull(_cursorIndexOfProfilePhotoPath)) {
          _item.profilePhotoPath = null;
        } else {
          _item.profilePhotoPath = _cursor.getString(_cursorIndexOfProfilePhotoPath);
        }
        _item.age = _cursor.getInt(_cursorIndexOfAge);
        _item.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        _item.bedNumber = _cursor.getInt(_cursorIndexOfBedNumber);
        _item.admissionDate = _cursor.getLong(_cursorIndexOfAdmissionDate);
        _item.amountOwed = _cursor.getDouble(_cursorIndexOfAmountOwed);
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Student getById(final int id) {
    final String _sql = "SELECT * FROM students WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
      final int _cursorIndexOfRegNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "regNumber");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfProfilePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePhotoPath");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfBedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "bedNumber");
      final int _cursorIndexOfAdmissionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "admissionDate");
      final int _cursorIndexOfAmountOwed = CursorUtil.getColumnIndexOrThrow(_cursor, "amountOwed");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _result.gender = null;
        } else {
          _result.gender = _cursor.getString(_cursorIndexOfGender);
        }
        if (_cursor.isNull(_cursorIndexOfCourse)) {
          _result.course = null;
        } else {
          _result.course = _cursor.getString(_cursorIndexOfCourse);
        }
        if (_cursor.isNull(_cursorIndexOfRegNumber)) {
          _result.regNumber = null;
        } else {
          _result.regNumber = _cursor.getString(_cursorIndexOfRegNumber);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _result.phone = null;
        } else {
          _result.phone = _cursor.getString(_cursorIndexOfPhone);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        if (_cursor.isNull(_cursorIndexOfProfilePhotoPath)) {
          _result.profilePhotoPath = null;
        } else {
          _result.profilePhotoPath = _cursor.getString(_cursorIndexOfProfilePhotoPath);
        }
        _result.age = _cursor.getInt(_cursorIndexOfAge);
        _result.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        _result.bedNumber = _cursor.getInt(_cursorIndexOfBedNumber);
        _result.admissionDate = _cursor.getLong(_cursorIndexOfAdmissionDate);
        _result.amountOwed = _cursor.getDouble(_cursorIndexOfAmountOwed);
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
  public Student login(final String email, final String password) {
    final String _sql = "SELECT * FROM students WHERE email=? AND password=? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
      final int _cursorIndexOfRegNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "regNumber");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfProfilePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePhotoPath");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfBedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "bedNumber");
      final int _cursorIndexOfAdmissionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "admissionDate");
      final int _cursorIndexOfAmountOwed = CursorUtil.getColumnIndexOrThrow(_cursor, "amountOwed");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _result.gender = null;
        } else {
          _result.gender = _cursor.getString(_cursorIndexOfGender);
        }
        if (_cursor.isNull(_cursorIndexOfCourse)) {
          _result.course = null;
        } else {
          _result.course = _cursor.getString(_cursorIndexOfCourse);
        }
        if (_cursor.isNull(_cursorIndexOfRegNumber)) {
          _result.regNumber = null;
        } else {
          _result.regNumber = _cursor.getString(_cursorIndexOfRegNumber);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _result.phone = null;
        } else {
          _result.phone = _cursor.getString(_cursorIndexOfPhone);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        if (_cursor.isNull(_cursorIndexOfProfilePhotoPath)) {
          _result.profilePhotoPath = null;
        } else {
          _result.profilePhotoPath = _cursor.getString(_cursorIndexOfProfilePhotoPath);
        }
        _result.age = _cursor.getInt(_cursorIndexOfAge);
        _result.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        _result.bedNumber = _cursor.getInt(_cursorIndexOfBedNumber);
        _result.admissionDate = _cursor.getLong(_cursorIndexOfAdmissionDate);
        _result.amountOwed = _cursor.getDouble(_cursorIndexOfAmountOwed);
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
  public Student getByRegNumber(final String regNo) {
    final String _sql = "SELECT * FROM students WHERE regNumber=? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (regNo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, regNo);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
      final int _cursorIndexOfRegNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "regNumber");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfProfilePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePhotoPath");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfBedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "bedNumber");
      final int _cursorIndexOfAdmissionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "admissionDate");
      final int _cursorIndexOfAmountOwed = CursorUtil.getColumnIndexOrThrow(_cursor, "amountOwed");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _result.gender = null;
        } else {
          _result.gender = _cursor.getString(_cursorIndexOfGender);
        }
        if (_cursor.isNull(_cursorIndexOfCourse)) {
          _result.course = null;
        } else {
          _result.course = _cursor.getString(_cursorIndexOfCourse);
        }
        if (_cursor.isNull(_cursorIndexOfRegNumber)) {
          _result.regNumber = null;
        } else {
          _result.regNumber = _cursor.getString(_cursorIndexOfRegNumber);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _result.phone = null;
        } else {
          _result.phone = _cursor.getString(_cursorIndexOfPhone);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        if (_cursor.isNull(_cursorIndexOfProfilePhotoPath)) {
          _result.profilePhotoPath = null;
        } else {
          _result.profilePhotoPath = _cursor.getString(_cursorIndexOfProfilePhotoPath);
        }
        _result.age = _cursor.getInt(_cursorIndexOfAge);
        _result.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        _result.bedNumber = _cursor.getInt(_cursorIndexOfBedNumber);
        _result.admissionDate = _cursor.getLong(_cursorIndexOfAdmissionDate);
        _result.amountOwed = _cursor.getDouble(_cursorIndexOfAmountOwed);
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
  public Student getByEmail(final String email) {
    final String _sql = "SELECT * FROM students WHERE email=? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfCourse = CursorUtil.getColumnIndexOrThrow(_cursor, "course");
      final int _cursorIndexOfRegNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "regNumber");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfProfilePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePhotoPath");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfBedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "bedNumber");
      final int _cursorIndexOfAdmissionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "admissionDate");
      final int _cursorIndexOfAmountOwed = CursorUtil.getColumnIndexOrThrow(_cursor, "amountOwed");
      final int _cursorIndexOfHostelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostelName");
      final int _cursorIndexOfRoomNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "roomNumber");
      final int _cursorIndexOfRoomType = CursorUtil.getColumnIndexOrThrow(_cursor, "roomType");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _result.gender = null;
        } else {
          _result.gender = _cursor.getString(_cursorIndexOfGender);
        }
        if (_cursor.isNull(_cursorIndexOfCourse)) {
          _result.course = null;
        } else {
          _result.course = _cursor.getString(_cursorIndexOfCourse);
        }
        if (_cursor.isNull(_cursorIndexOfRegNumber)) {
          _result.regNumber = null;
        } else {
          _result.regNumber = _cursor.getString(_cursorIndexOfRegNumber);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _result.password = null;
        } else {
          _result.password = _cursor.getString(_cursorIndexOfPassword);
        }
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _result.phone = null;
        } else {
          _result.phone = _cursor.getString(_cursorIndexOfPhone);
        }
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _result.role = null;
        } else {
          _result.role = _cursor.getString(_cursorIndexOfRole);
        }
        if (_cursor.isNull(_cursorIndexOfProfilePhotoPath)) {
          _result.profilePhotoPath = null;
        } else {
          _result.profilePhotoPath = _cursor.getString(_cursorIndexOfProfilePhotoPath);
        }
        _result.age = _cursor.getInt(_cursorIndexOfAge);
        _result.roomId = _cursor.getInt(_cursorIndexOfRoomId);
        _result.bedNumber = _cursor.getInt(_cursorIndexOfBedNumber);
        _result.admissionDate = _cursor.getLong(_cursorIndexOfAdmissionDate);
        _result.amountOwed = _cursor.getDouble(_cursorIndexOfAmountOwed);
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
