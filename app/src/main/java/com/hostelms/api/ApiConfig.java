package com.hostelms.api;

/**
 * Central configuration for all PHP API endpoints.
 * CHANGE BASE_URL to your server before deploying.
 */
public class ApiConfig {
    // ── ⚠️  Change this to your PHP server address ──────────────────────
    public static final String BASE_URL = "http://192.168.1.105/hostelms/backend/api/";

    // Auth
    public static final String REGISTER          = BASE_URL + "register.php";
    public static final String LOGIN             = BASE_URL + "login.php";
    public static final String ADMIN_LOGIN       = BASE_URL + "admin_login.php";

    // Hostels
    public static final String GET_HOSTELS       = BASE_URL + "get_hostels.php";
    public static final String GET_HOSTEL_DETAIL = BASE_URL + "get_hostel_detail.php";
    public static final String ADD_HOSTEL        = BASE_URL + "add_hostel.php";
    public static final String UPDATE_HOSTEL     = BASE_URL + "update_hostel.php";
    public static final String DELETE_HOSTEL     = BASE_URL + "delete_hostel.php";

    // Rooms
    public static final String ADD_ROOM          = BASE_URL + "add_room.php";
    public static final String GET_ROOMS         = BASE_URL + "get_rooms.php";
    public static final String UPDATE_ROOM       = BASE_URL + "update_room.php";
    public static final String DELETE_ROOM       = BASE_URL + "delete_room.php";

    // Bookings
    public static final String BOOK_ROOM         = BASE_URL + "book_room.php";
    public static final String GET_BOOKINGS      = BASE_URL + "get_bookings.php";

    // Checklist (NEW – room inspection after booking)
    public static final String SUBMIT_CHECKLIST  = BASE_URL + "submit_checklist.php";

    // Complaints
    public static final String SUBMIT_COMPLAINT  = BASE_URL + "submit_complaint.php";
    public static final String GET_COMPLAINTS    = BASE_URL + "get_complaints.php";
    public static final String UPDATE_COMPLAINT  = BASE_URL + "update_complaint.php";

    // Announcements
    public static final String GET_ANNOUNCEMENTS    = BASE_URL + "get_announcements.php";
    public static final String ADD_ANNOUNCEMENT     = BASE_URL + "add_announcement.php";
    public static final String UPDATE_ANNOUNCEMENT  = BASE_URL + "update_announcement.php";
    public static final String DELETE_ANNOUNCEMENT  = BASE_URL + "delete_announcement.php";

    // Students (admin)
    public static final String GET_STUDENTS      = BASE_URL + "get_students.php";
    public static final String UPDATE_STUDENT    = BASE_URL + "update_student.php";
    public static final String DELETE_STUDENT    = BASE_URL + "delete_student.php";
    public static final String ASSIGN_ROOM       = BASE_URL + "assign_room.php";
}
