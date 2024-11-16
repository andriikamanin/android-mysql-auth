<?php
include 'config.php';

$query = "SELECT * FROM users";
$result = $conn->query($query);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        echo "ID: " . $row["id"] . " - Username: " . $row["username"] . " - Email: " . $row["email"] . "<br>";
    }
} else {
    echo "No users found.";
}

$conn->close();
?>
