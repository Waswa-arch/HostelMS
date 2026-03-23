<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$stmt = $pdo->prepare("INSERT INTO complaints
    (student_id,category,subject,description,priority)
    VALUES (?,?,?,?,?)");
$stmt->execute([
    intval($_POST['student_id']??0),
    trim($_POST['category']??'Other'),
    trim($_POST['subject']??''),
    trim($_POST['description']??''),
    trim($_POST['priority']??'Normal')
]);
echo json_encode(['status'=>'success','message'=>'Complaint submitted']);
?>