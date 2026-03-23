<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$pdo->prepare("INSERT INTO announcements (admin_id,title,message,is_urgent) VALUES (?,?,?,?)")
    ->execute([
        intval($_POST['admin_id']??0),
        trim($_POST['title']??''),
        trim($_POST['message']??''),
        intval($_POST['is_urgent']??0)
    ]);
echo json_encode(['status'=>'success','message'=>'Announcement posted']);
?>