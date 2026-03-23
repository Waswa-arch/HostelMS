package com.hostelms.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "HostelMSSession";
    private static final String KEY_ID = "userId";
    private static final String KEY_ROLE = "userRole";
    private static final String KEY_NAME = "userName";
    private static final String KEY_EMAIL = "userEmail";
    private static final String KEY_LOGGED = "isLoggedIn";

    private final SharedPreferences prefs;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void createSession(int id, String role, String name, String email) {
        prefs.edit().putInt(KEY_ID, id).putString(KEY_ROLE, role)
             .putString(KEY_NAME, name).putString(KEY_EMAIL, email)
             .putBoolean(KEY_LOGGED, true).apply();
    }

    public void logout() { prefs.edit().clear().apply(); }
    public boolean isLoggedIn() { return prefs.getBoolean(KEY_LOGGED, false); }
    public int getUserId() { return prefs.getInt(KEY_ID, -1); }
    public String getRole() { return prefs.getString(KEY_ROLE, ""); }
    public String getName() { return prefs.getString(KEY_NAME, ""); }
    public String getEmail() { return prefs.getString(KEY_EMAIL, ""); }
}
