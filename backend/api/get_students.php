<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$rows = $pdo->query("SELECT id,name,reg_number,phone,email,room_id,status,course,age
    FROM students ORDER BY name")->fetchAll();
echo json_encode($rows);
?>