<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$sid    = intval($_POST['student_id']??0);
$rid    = intval($_POST['room_id']??0);
$hid    = intval($_POST['hostel_id']??0);
$cin    = trim($_POST['check_in']??'');
$cout   = trim($_POST['check_out']??'');

// Check availability
$r = $pdo->prepare("SELECT * FROM rooms WHERE id=?");
$r->execute([$rid]); $room = $r->fetch();
if (!$room || $room['status'] !== 'available') {
    echo json_encode(['status'=>'error','message'=>'Room not available']); exit;
}

// Insert booking
$stmt = $pdo->prepare("INSERT INTO bookings
    (student_id,room_id,hostel_id,check_in,check_out,total_price,status)
    VALUES (?,?,?,?,?,?,?)");
$stmt->execute([$sid,$rid,$hid,$cin,$cout,$room['price'],'confirmed']);

// Mark room occupied + update student
$pdo->prepare("UPDATE rooms SET occupied=occupied+1,
    status=IF(occupied+1>=capacity,'occupied','available') WHERE id=?")
    ->execute([$rid]);
$pdo->prepare("UPDATE students SET room_id=? WHERE id=?")->execute([$rid,$sid]);

echo json_encode(['status'=>'success','message'=>'Booking confirmed',
                  'booking_id'=>$pdo->lastInsertId()]);
?>