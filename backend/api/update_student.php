<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$pdo->prepare("UPDATE students SET name=?,phone=?,email=?,room_id=? WHERE id=?")
    ->execute([
        trim($_POST['name']??''),
        trim($_POST['phone']??''),
        trim($_POST['email']??''),
        ($_POST['room_id']??'')=='' ? null : intval($_POST['room_id']),
        intval($_POST['student_id']??0)
    ]);
echo json_encode(['status'=>'success','message'=>'Student updated']);
?>