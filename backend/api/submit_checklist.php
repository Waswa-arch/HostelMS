<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') exit(0);
require_once '../db.php';

$student_id    = intval($_POST['student_id']    ?? 0);
$booking_id    = intval($_POST['booking_id']    ?? 0);
$items_checked = intval($_POST['items_checked'] ?? 0);
$total_items   = intval($_POST['total_items']   ?? 0);
$summary       = trim($_POST['summary']         ?? '');

// Upsert into room_checklists table
try {
    $pdo->prepare("INSERT INTO room_checklists
        (student_id, booking_id, items_checked, total_items, summary)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            items_checked = VALUES(items_checked),
            total_items   = VALUES(total_items),
            summary       = VALUES(summary),
            submitted_at  = CURRENT_TIMESTAMP")
        ->execute([$student_id, $booking_id, $items_checked, $total_items, $summary]);

    // Mark the booking as checked in
    if ($booking_id > 0) {
        $pdo->prepare("UPDATE bookings SET status = 'checked_in' WHERE id = ?")
            ->execute([$booking_id]);
    }

    echo json_encode(['status' => 'success', 'message' => 'Checklist saved']);
} catch (PDOException $e) {
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
}
?>