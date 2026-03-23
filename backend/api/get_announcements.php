<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$rows = $pdo->query("SELECT a.*, ad.name AS admin_name,
    UNIX_TIMESTAMP(a.created_at)*1000 AS created_at_ts
    FROM announcements a JOIN admins ad ON a.admin_id=ad.id
    ORDER BY a.created_at DESC")->fetchAll();
echo json_encode($rows);
?>