<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$reg  = trim($_POST['reg_number'] ?? '');
$pass = trim($_POST['password'] ?? '');

if (!$reg || !$pass) {
    echo json_encode(['status'=>'error','message'=>'Fields required']); exit;
}

$stmt = $pdo->prepare("SELECT * FROM students WHERE reg_number = ?");
$stmt->execute([$reg]);
$s = $stmt->fetch();

if ($s && password_verify($pass, $s['password'])) {
    unset($s['password']);
    echo json_encode(['status'=>'success','user'=>$s]);
} else {
    echo json_encode(['status'=>'error','message'=>'Invalid credentials']);
}
?>