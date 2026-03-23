<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$hid = intval($_GET['hostel_id'] ?? 0);
$s1  = $pdo->prepare("SELECT * FROM hostels WHERE id=?");
$s1->execute([$hid]); $hostel = $s1->fetch();

$s2 = $pdo->prepare("SELECT * FROM rooms WHERE hostel_id=?");
$s2->execute([$hid]); $rooms = $s2->fetchAll();

echo json_encode(['hostel'=>$hostel,'rooms'=>$rooms]);
?>