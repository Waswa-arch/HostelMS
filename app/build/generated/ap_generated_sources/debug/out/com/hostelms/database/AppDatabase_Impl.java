package com.hostelms.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.hostelms.database.dao.AnnouncementDao;
import com.hostelms.database.dao.AnnouncementDao_Impl;
import com.hostelms.database.dao.AttendanceDao;
import com.hostelms.database.dao.AttendanceDao_Impl;
import com.hostelms.database.dao.BookingDao;
import com.hostelms.database.dao.BookingDao_Impl;
import com.hostelms.database.dao.ComplaintDao;
import com.hostelms.database.dao.ComplaintDao_Impl;
import com.hostelms.database.dao.RoomDao;
import com.hostelms.database.dao.RoomDao_Impl;
import com.hostelms.database.dao.StudentDao;
import com.hostelms.database.dao.StudentDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile StudentDao _studentDao;

  private volatile RoomDao _roomDao;

  private volatile ComplaintDao _complaintDao;

  private volatile AnnouncementDao _announcementDao;

  private volatile AttendanceDao _attendanceDao;

  private volatile BookingDao _bookingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `students` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `gender` TEXT, `course` TEXT, `regNumber` TEXT, `email` TEXT, `password` TEXT, `phone` TEXT, `role` TEXT, `profilePhotoPath` TEXT, `age` INTEGER NOT NULL, `roomId` INTEGER NOT NULL, `bedNumber` INTEGER NOT NULL, `admissionDate` INTEGER NOT NULL, `amountOwed` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rooms` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `hostelName` TEXT, `roomNumber` TEXT, `roomType` TEXT, `amenities` TEXT, `status` TEXT, `gender` TEXT, `capacity` INTEGER NOT NULL, `occupied` INTEGER NOT NULL, `pricePerSemester` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `complaints` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `studentName` TEXT, `category` TEXT, `subject` TEXT, `description` TEXT, `priority` TEXT, `status` TEXT, `adminResponse` TEXT, `dateSubmitted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `announcements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `body` TEXT, `author` TEXT, `isUrgent` INTEGER NOT NULL, `datePosted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attendance` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `studentName` TEXT, `location` TEXT, `type` TEXT, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bookings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `roomId` INTEGER NOT NULL, `hostelName` TEXT, `roomNumber` TEXT, `roomType` TEXT, `mealBundle` TEXT, `status` TEXT, `totalPrice` REAL NOT NULL, `bookingDate` INTEGER NOT NULL, `checkInDone` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '49c8c4403e9b3af24f6560dbbf498f64')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `students`");
        db.execSQL("DROP TABLE IF EXISTS `rooms`");
        db.execSQL("DROP TABLE IF EXISTS `complaints`");
        db.execSQL("DROP TABLE IF EXISTS `announcements`");
        db.execSQL("DROP TABLE IF EXISTS `attendance`");
        db.execSQL("DROP TABLE IF EXISTS `bookings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsStudents = new HashMap<String, TableInfo.Column>(15);
        _columnsStudents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("gender", new TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("course", new TableInfo.Column("course", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("regNumber", new TableInfo.Column("regNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("role", new TableInfo.Column("role", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("profilePhotoPath", new TableInfo.Column("profilePhotoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("roomId", new TableInfo.Column("roomId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("bedNumber", new TableInfo.Column("bedNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("admissionDate", new TableInfo.Column("admissionDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("amountOwed", new TableInfo.Column("amountOwed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudents = new TableInfo("students", _columnsStudents, _foreignKeysStudents, _indicesStudents);
        final TableInfo _existingStudents = TableInfo.read(db, "students");
        if (!_infoStudents.equals(_existingStudents)) {
          return new RoomOpenHelper.ValidationResult(false, "students(com.hostelms.database.entities.Student).\n"
                  + " Expected:\n" + _infoStudents + "\n"
                  + " Found:\n" + _existingStudents);
        }
        final HashMap<String, TableInfo.Column> _columnsRooms = new HashMap<String, TableInfo.Column>(10);
        _columnsRooms.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("hostelName", new TableInfo.Column("hostelName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("roomNumber", new TableInfo.Column("roomNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("roomType", new TableInfo.Column("roomType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("amenities", new TableInfo.Column("amenities", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("gender", new TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("capacity", new TableInfo.Column("capacity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("occupied", new TableInfo.Column("occupied", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRooms.put("pricePerSemester", new TableInfo.Column("pricePerSemester", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRooms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRooms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRooms = new TableInfo("rooms", _columnsRooms, _foreignKeysRooms, _indicesRooms);
        final TableInfo _existingRooms = TableInfo.read(db, "rooms");
        if (!_infoRooms.equals(_existingRooms)) {
          return new RoomOpenHelper.ValidationResult(false, "rooms(com.hostelms.database.entities.Room).\n"
                  + " Expected:\n" + _infoRooms + "\n"
                  + " Found:\n" + _existingRooms);
        }
        final HashMap<String, TableInfo.Column> _columnsComplaints = new HashMap<String, TableInfo.Column>(10);
        _columnsComplaints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("studentName", new TableInfo.Column("studentName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("subject", new TableInfo.Column("subject", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("priority", new TableInfo.Column("priority", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("adminResponse", new TableInfo.Column("adminResponse", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComplaints.put("dateSubmitted", new TableInfo.Column("dateSubmitted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysComplaints = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesComplaints = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoComplaints = new TableInfo("complaints", _columnsComplaints, _foreignKeysComplaints, _indicesComplaints);
        final TableInfo _existingComplaints = TableInfo.read(db, "complaints");
        if (!_infoComplaints.equals(_existingComplaints)) {
          return new RoomOpenHelper.ValidationResult(false, "complaints(com.hostelms.database.entities.Complaint).\n"
                  + " Expected:\n" + _infoComplaints + "\n"
                  + " Found:\n" + _existingComplaints);
        }
        final HashMap<String, TableInfo.Column> _columnsAnnouncements = new HashMap<String, TableInfo.Column>(6);
        _columnsAnnouncements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("body", new TableInfo.Column("body", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("author", new TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("isUrgent", new TableInfo.Column("isUrgent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnouncements.put("datePosted", new TableInfo.Column("datePosted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnnouncements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAnnouncements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAnnouncements = new TableInfo("announcements", _columnsAnnouncements, _foreignKeysAnnouncements, _indicesAnnouncements);
        final TableInfo _existingAnnouncements = TableInfo.read(db, "announcements");
        if (!_infoAnnouncements.equals(_existingAnnouncements)) {
          return new RoomOpenHelper.ValidationResult(false, "announcements(com.hostelms.database.entities.Announcement).\n"
                  + " Expected:\n" + _infoAnnouncements + "\n"
                  + " Found:\n" + _existingAnnouncements);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendance = new HashMap<String, TableInfo.Column>(6);
        _columnsAttendance.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("studentName", new TableInfo.Column("studentName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendance = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAttendance = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAttendance = new TableInfo("attendance", _columnsAttendance, _foreignKeysAttendance, _indicesAttendance);
        final TableInfo _existingAttendance = TableInfo.read(db, "attendance");
        if (!_infoAttendance.equals(_existingAttendance)) {
          return new RoomOpenHelper.ValidationResult(false, "attendance(com.hostelms.database.entities.Attendance).\n"
                  + " Expected:\n" + _infoAttendance + "\n"
                  + " Found:\n" + _existingAttendance);
        }
        final HashMap<String, TableInfo.Column> _columnsBookings = new HashMap<String, TableInfo.Column>(11);
        _columnsBookings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("roomId", new TableInfo.Column("roomId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("hostelName", new TableInfo.Column("hostelName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("roomNumber", new TableInfo.Column("roomNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("roomType", new TableInfo.Column("roomType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("mealBundle", new TableInfo.Column("mealBundle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("totalPrice", new TableInfo.Column("totalPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("bookingDate", new TableInfo.Column("bookingDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookings.put("checkInDone", new TableInfo.Column("checkInDone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBookings = new TableInfo("bookings", _columnsBookings, _foreignKeysBookings, _indicesBookings);
        final TableInfo _existingBookings = TableInfo.read(db, "bookings");
        if (!_infoBookings.equals(_existingBookings)) {
          return new RoomOpenHelper.ValidationResult(false, "bookings(com.hostelms.database.entities.Booking).\n"
                  + " Expected:\n" + _infoBookings + "\n"
                  + " Found:\n" + _existingBookings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "49c8c4403e9b3af24f6560dbbf498f64", "c47e51223a9c8075fadd9aed8bc41483");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "students","rooms","complaints","announcements","attendance","bookings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `students`");
      _db.execSQL("DELETE FROM `rooms`");
      _db.execSQL("DELETE FROM `complaints`");
      _db.execSQL("DELETE FROM `announcements`");
      _db.execSQL("DELETE FROM `attendance`");
      _db.execSQL("DELETE FROM `bookings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(StudentDao.class, StudentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RoomDao.class, RoomDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ComplaintDao.class, ComplaintDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AnnouncementDao.class, AnnouncementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AttendanceDao.class, AttendanceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BookingDao.class, BookingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public StudentDao studentDao() {
    if (_studentDao != null) {
      return _studentDao;
    } else {
      synchronized(this) {
        if(_studentDao == null) {
          _studentDao = new StudentDao_Impl(this);
        }
        return _studentDao;
      }
    }
  }

  @Override
  public RoomDao roomDao() {
    if (_roomDao != null) {
      return _roomDao;
    } else {
      synchronized(this) {
        if(_roomDao == null) {
          _roomDao = new RoomDao_Impl(this);
        }
        return _roomDao;
      }
    }
  }

  @Override
  public ComplaintDao complaintDao() {
    if (_complaintDao != null) {
      return _complaintDao;
    } else {
      synchronized(this) {
        if(_complaintDao == null) {
          _complaintDao = new ComplaintDao_Impl(this);
        }
        return _complaintDao;
      }
    }
  }

  @Override
  public AnnouncementDao announcementDao() {
    if (_announcementDao != null) {
      return _announcementDao;
    } else {
      synchronized(this) {
        if(_announcementDao == null) {
          _announcementDao = new AnnouncementDao_Impl(this);
        }
        return _announcementDao;
      }
    }
  }

  @Override
  public AttendanceDao attendanceDao() {
    if (_attendanceDao != null) {
      return _attendanceDao;
    } else {
      synchronized(this) {
        if(_attendanceDao == null) {
          _attendanceDao = new AttendanceDao_Impl(this);
        }
        return _attendanceDao;
      }
    }
  }

  @Override
  public BookingDao bookingDao() {
    if (_bookingDao != null) {
      return _bookingDao;
    } else {
      synchronized(this) {
        if(_bookingDao == null) {
          _bookingDao = new BookingDao_Impl(this);
        }
        return _bookingDao;
      }
    }
  }
}
