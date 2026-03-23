<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$pdo->prepare("DELETE FROM students WHERE id=?")
    ->execute([intval($_POST['student_id']??0)]);
echo json_encode(['status'=>'success']);
?>