<?php
$host = "usersdatabase.chsygay2isy8.eu-central-1.rds.amazonaws.com"; // Адрес сервера
$username = "admin"; // Имя пользователя MySQL
$password = "Kamanin2006"; // Пароль MySQL (оставьте пустым, если без пароля)
$dbname = "user_database"; // Название базы данных

// Подключение к базе данных
$conn = new mysqli($host, $username, $password, $dbname);

// Проверка соединения
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
