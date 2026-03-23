<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$stmt = $pdo->prepare("INSERT INTO hostels
    (name,description,image_url,person_in_charge,contact_number,address)
    VALUES (?,?,?,?,?,?)");
$stmt->execute([
    trim($_POST['name']??''), trim($_POST['description']??''),
    trim($_POST['image_url']??''), trim($_POST['person_in_charge']??''),
    trim($_POST['contact_number']??''), trim($_POST['address']??'')
]);
echo json_encode(['status'=>'success','id'=>$pdo->lastInsertId()]);
?>