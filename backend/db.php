<?php
// ── Database configuration ──────────────────────────────────────────────────
$host     = 'localhost';
$dbname   = 'hostelms';
$username = 'root';
$password = '';          // change in production

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4",
                   $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['status' => 'error', 'message' => 'DB connection failed']);
    exit;
}
?>
