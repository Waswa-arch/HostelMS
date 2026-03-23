package com.hostelms.models;

/** Plain model for room data received from get_hostel_detail.php. */
public class HostelRoom {
    public int    id;
    public String number;
    public String type;
    public String price;
    public String amenities;
    public String status;   // "available" | "occupied" | "maintenance"
}
