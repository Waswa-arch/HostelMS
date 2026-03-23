<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$name   = trim($_POST['name'] ?? '');
$reg    = trim($_POST['reg_number'] ?? '');
$phone  = trim($_POST['phone'] ?? '');
$email  = trim($_POST['email'] ?? '');
$pass   = trim($_POST['password'] ?? '');
$gender = trim($_POST['gender'] ?? '');
$course = trim($_POST['course'] ?? '');
$age    = intval($_POST['age'] ?? 0);

if (!$name || !$reg || !$phone || !$email || !$pass) {
    echo json_encode(['status'=>'error','message'=>'Required fields missing']); exit;
}

$hashed = password_hash($pass, PASSWORD_BCRYPT);
try {
    $stmt = $pdo->prepare("INSERT INTO students (name,reg_number,phone,email,password,gender,course,age)
                           VALUES (?,?,?,?,?,?,?,?)");
    $stmt->execute([$name,$reg,$phone,$email,$hashed,$gender,$course,$age]);
    echo json_encode(['status'=>'success','message'=>'Registered successfully',
                      'id'=>$pdo->lastInsertId()]);
} catch (PDOException $e) {
    echo json_encode(['status'=>'error','message'=>'Registration number or email already exists']);
}
?>