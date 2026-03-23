<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$sid = intval($_GET['student_id']??0);
$stmt = $pdo->prepare("SELECT b.*,h.name AS hostel_name,r.room_number
    FROM bookings b
    JOIN hostels h ON b.hostel_id=h.id
    JOIN rooms r ON b.room_id=r.id
    WHERE b.student_id=? ORDER BY b.created_at DESC");
$stmt->execute([$sid]);
echo json_encode($stmt->fetchAll());
?>