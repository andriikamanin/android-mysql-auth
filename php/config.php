<?php
$host = "localhost"; // Адрес сервера
$username = "root"; // Имя пользователя MySQL
$password = ""; // Пароль MySQL (оставьте пустым, если без пароля)
$dbname = "user_database"; // Название базы данных

// Подключение к базе данных
$conn = new mysqli($host, $username, $password, $dbname);

// Проверка соединения
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
