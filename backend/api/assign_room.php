<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$sid = intval($_POST['student_id']??0);
$rid = intval($_POST['room_id']??0);
$pdo->prepare("UPDATE students SET room_id=? WHERE id=?")->execute([$rid,$sid]);
$pdo->prepare("UPDATE rooms SET occupied=occupied+1 WHERE id=?")->execute([$rid]);
echo json_encode(['status'=>'success','message'=>'Room assigned']);
?>