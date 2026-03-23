<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$pdo->prepare("UPDATE complaints SET status=?,admin_response=? WHERE id=?")
    ->execute([
        trim($_POST['status']??'Open'),
        trim($_POST['admin_response']??''),
        intval($_POST['complaint_id']??0)
    ]);
echo json_encode(['status'=>'success']);
?>