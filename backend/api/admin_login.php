<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$user = trim($_POST['username'] ?? '');
$pass = trim($_POST['password'] ?? '');

$stmt = $pdo->prepare("SELECT * FROM admins WHERE username = ? OR email = ?");
$stmt->execute([$user,$user]);
$a = $stmt->fetch();

if ($a && password_verify($pass, $a['password'])) {
    unset($a['password']);
    echo json_encode(['status'=>'success','admin'=>$a]);
} else {
    echo json_encode(['status'=>'error','message'=>'Invalid credentials']);
}
?>