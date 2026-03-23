<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$stmt = $pdo->prepare("INSERT INTO rooms
    (hostel_id,room_number,type,price,amenities,capacity,gender)
    VALUES (?,?,?,?,?,?,?)");
$stmt->execute([
    intval($_POST['hostel_id']??0), trim($_POST['room_number']??''),
    trim($_POST['type']??'Single'), floatval($_POST['price']??0),
    trim($_POST['amenities']??''), intval($_POST['capacity']??1),
    trim($_POST['gender']??'Mixed')
]);
echo json_encode(['status'=>'success','id'=>$pdo->lastInsertId()]);
?>