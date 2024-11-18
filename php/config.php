<?php
$host = "your_host"; // Адрес сервера
$username = "your_username"; // Имя пользователя MySQL
$password = "your_password"; // Пароль MySQL (оставьте пустым, если без пароля)
$dbname = "your_database"; // Название базы данных

// Подключение к базе данных
$conn = new mysqli($host, $username, $password, $dbname);

// Проверка соединения
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
