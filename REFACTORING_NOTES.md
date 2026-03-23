# HostelMS – Refactoring Notes
*What changed, what was kept, and why.*

---

## Overview

The existing project used **Android Room (local SQLite)** as its sole data source.
The refactored version adds a **PHP + MySQL API layer** (via Volley) while keeping
Room as a local cache / offline fallback. No existing code was deleted.

---

## Files UNCHANGED (kept 100% as-is)

| File | Reason |
|------|--------|
| `SplashActivity.java` | Works correctly; auto-routes by session role |
| `RoleSelectActivity.java` | Role selection flow unchanged |
| `AdminDashboardActivity.java` | All 4 required modules already present |
| `ManageRoomsActivity.java` | Full CRUD via local DB; API layer additive |
| `AddEditRoomActivity.java` | Retained; admins can still add rooms locally |
| `ManageStudentsActivity.java` | Retained; now backed by API through updated entities |
| `AddEditStudentActivity.java` | Retained; admin student management intact |
| `ManageComplaintsActivity.java` | Retained; view/respond to complaints |
| `AllocateRoomActivity.java` | Retained; room assignment flow kept |
| `AttendanceReportActivity.java` | QR + attendance system (bonus feature kept) |
| `QrGenerateActivity.java` | Retained |
| `QrScanActivity.java` | Retained |
| `CheckInActivity.java` | Room checklist feature kept |
| `StudentProfileActivity.java` | Profile editing kept |
| All adapters (original 5) | Kept intact; new adapters added alongside |
| All Room entities + DAOs | Local DB kept as offline fallback |
| `AppDatabase.java` | Unchanged |
| `SessionManager.java` | Unchanged |
| `DateUtils.java` | Unchanged |
| `SeedData.java` | Unchanged (seeds local DB for demo/offline) |
| All existing XML layouts | Kept; only `activity_login`, `activity_register`, `activity_student_dashboard`, `activity_complaint`, `activity_announcements`, `activity_add_announcement` were updated |

---

## Files MODIFIED

### `LoginActivity.java`
**Why:** Spec requires student login by **Registration Number**, not email.
**Change:** Student path now sends `reg_number` + `password` to `login.php`. Falls back to local `studentDao().getByRegNumber()` on network failure. Label dynamically changes to "REGISTRATION NUMBER" for student role.

### `RegisterActivity.java`
**Why:** Spec requires fields: name, reg_number, phone, email, password.
**Change:** Phone field is now **required** (not optional). Posts to `register.php`. Falls back to local insert on network failure.

### `StudentDashboardActivity.java`
**Why:** Spec requires: Booking, Inquiry, Issues, FAQ, Announcements, How to Use.
**Change:** `cardBooking` → `HostelListActivity(mode=booking)`, new `cardInquiry` → `HostelListActivity(mode=inquiry)`, new `cardFaq`, `cardHowToUse`. All original cards (Check-in, QR Scan, Profile) retained.

### `ComplaintActivity.java`
**Why:** Must submit to `submit_complaint.php` API.
**Change:** Added API call before local save. Both paths result in local storage so offline complaints are preserved. ProgressBar added.

### `AnnouncementsActivity.java`
**Why:** Must fetch from `get_announcements.php` API.
**Change:** Tries API first; falls back to local `announcementDao().getAll()`. ProgressBar added.

### `AddAnnouncementActivity.java`
**Why:** Must post to `add_announcement.php` API.
**Change:** Posts to API; also saves locally for consistent display. ProgressBar added.

### `activity_login.xml`
**Change:** Added `tvEmailLabel` (dynamic label), `progressBar`.

### `activity_register.xml`
**Change:** Phone field moved up and marked required. `progressBar` added.

### `activity_student_dashboard.xml`
**Change:** Added `cardInquiry`, `cardFaq`, `cardHowToUse` cards. Row layout reorganised to 2-per-row grid.

### `activity_complaint.xml`, `activity_announcements.xml`, `activity_add_announcement.xml`
**Change:** `progressBar` added to each.

### `app/build.gradle`
**Change:** Added `volley:1.2.1` and `glide:4.16.0` dependencies.

### `AndroidManifest.xml`
**Change:** Added `INTERNET`, `ACCESS_NETWORK_STATE` permissions, `usesCleartextTraffic="true"`. Registered 6 new activities.

---

## Files ADDED (new)

### Java
| File | Purpose |
|------|---------|
| `api/ApiConfig.java` | Centralised API endpoint constants |
| `api/ApiClient.java` | Volley POST/GET wrapper |
| `models/Hostel.java` | POJO for API hostel data |
| `models/HostelRoom.java` | POJO for API room data |
| `activities/HostelListActivity.java` | Booking + Inquiry entry point |
| `activities/HostelDetailActivity.java` | Hostel detail + room list |
| `activities/BookRoomActivity.java` | Booking confirmation (date picker + API) |
| `activities/BookingConfirmationActivity.java` | Success screen |
| `activities/FaqActivity.java` | Static FAQ |
| `activities/HowToUseActivity.java` | Step-by-step guide |
| `adapters/HostelAdapter.java` | RecyclerView for hostel cards with Glide images |
| `adapters/RoomApiAdapter.java` | RecyclerView for API rooms |

### Layouts
| File | Purpose |
|------|---------|
| `activity_hostel_list.xml` | Hostel list |
| `activity_hostel_detail.xml` | Hostel header + rooms |
| `activity_book_room.xml` | Booking form |
| `activity_booking_confirmation.xml` | Confirmation screen |
| `activity_faq.xml` | FAQ content |
| `activity_how_to_use.xml` | How-to content |
| `item_hostel.xml` | Hostel card (image + name + contact) |
| `item_room_api.xml` | API room card |

### Backend
| File | Purpose |
|------|---------|
| `backend/db.php` | PDO connection |
| `backend/schema.sql` | Full MySQL schema + seed data |
| `backend/api/register.php` | Student registration |
| `backend/api/login.php` | Student login by reg_number |
| `backend/api/admin_login.php` | Admin login |
| `backend/api/get_hostels.php` | List all hostels |
| `backend/api/get_hostel_detail.php` | Hostel + room detail |
| `backend/api/add_hostel.php` | Add hostel |
| `backend/api/add_room.php` | Add room |
| `backend/api/book_room.php` | Book room + update occupancy |
| `backend/api/get_bookings.php` | Student booking history |
| `backend/api/submit_complaint.php` | Submit complaint |
| `backend/api/get_complaints.php` | All complaints (admin) |
| `backend/api/update_complaint.php` | Update status + respond |
| `backend/api/get_announcements.php` | List announcements |
| `backend/api/add_announcement.php` | Post announcement |
| `backend/api/delete_announcement.php` | Delete announcement |
| `backend/api/get_students.php` | List students (admin) |
| `backend/api/update_student.php` | Edit student (admin) |
| `backend/api/delete_student.php` | Delete student (admin) |
| `backend/api/assign_room.php` | Assign room to student |

---

## Navigation Flow (final)

### Student App
```
SplashActivity
  └─ RoleSelectActivity ──────────────── (if not logged in)
       ├─ LoginActivity (role=student)
       │    └─ RegisterActivity
       └─ LoginActivity (role=admin)

StudentDashboardActivity
  ├─ HostelListActivity (mode=booking)
  │    └─ HostelDetailActivity
  │         └─ BookRoomActivity
  │              └─ BookingConfirmationActivity
  ├─ HostelListActivity (mode=inquiry)  ← contact details shown
  ├─ ComplaintActivity
  ├─ AnnouncementsActivity
  ├─ FaqActivity
  ├─ HowToUseActivity
  ├─ CheckInActivity          ← retained
  ├─ QrScanActivity           ← retained
  └─ StudentProfileActivity   ← retained
```

### Admin App
```
AdminDashboardActivity
  ├─ ManageStudentsActivity → AddEditStudentActivity
  ├─ ManageRoomsActivity    → AddEditRoomActivity
  ├─ AllocateRoomActivity
  ├─ ManageComplaintsActivity
  ├─ AddAnnouncementActivity
  ├─ AttendanceReportActivity  ← retained
  └─ QrGenerateActivity        ← retained
```

---

## Setup Instructions

### Backend
1. Import `backend/schema.sql` into MySQL (phpMyAdmin or CLI)
2. Edit `backend/db.php` — set your host/username/password
3. Deploy the `backend/` folder to your server (e.g. `htdocs/hostelms/`)

### Android
1. Open the project in Android Studio
2. In `api/ApiConfig.java`, set `BASE_URL` to your server:
   ```java
   public static final String BASE_URL = "http://192.168.1.x/hostelms/backend/api/";
   ```
3. Build and run — the app works offline with local Room DB if the server is unreachable.

### Default admin login
- Username: `admin`
- Password: `admin123`
