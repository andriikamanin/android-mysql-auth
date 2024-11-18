<?php
include 'config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = $_POST['email'] ?? '';
    $username = $_POST['username'] ?? '';
    $user_avatar = $_POST['user_avatar'] ?? '';

    if (empty($email) || empty($username)) {
        echo json_encode(["success" => false, "message" => "Email and Username are required"]);
        exit;
    }

    $query = "UPDATE users SET username = ?, user_avatar = ? WHERE email = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("sss", $username, $user_avatar, $email);

    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "User updated successfully"]);
    } else {
        echo json_encode(["success" => false, "message" => "Update failed"]);
    }

    $stmt->close();
}
$conn->close();
?>
