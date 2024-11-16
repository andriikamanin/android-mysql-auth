# android-mysql-auth
"Open-source Android app with MySQL-based authentication."


# Android Registration and Login App with PHP Backend

This project is an open-source Android application for user registration and login, fully integrated with a PHP backend and MySQL database. It demonstrates how to build a simple authentication system with Android and a backend API.

## Features
- User Registration:
  - Stores user details (username, email, password) securely in a MySQL database.
- User Login:
  - Validates user credentials and retrieves user information.
- PHP Backend:
  - Built with secure practices (password hashing and prepared statements).
- Android Frontend:
  - Uses Retrofit for API communication with the backend.

---

## Requirements

### Android
- Android Studio (latest version recommended)
- Minimum SDK version: 21 (Lollipop)
- Retrofit library integrated into the project

### Backend
- PHP Server (XAMPP, LAMP, WAMP, or any other PHP server)
- MySQL database

---

## Setup Instructions

### 1. Backend Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/<your-repo-name>.git
   ```
2. **Move PHP files to the server:**
   - Navigate to the `php` folder in the project directory.
   - Copy the contents of this folder to your PHP server's root directory. For XAMPP:
     ```bash
     cp -r php/* /opt/lampp/htdocs/myproject/
     ```

3. **Set up the database:**
   - Open **phpMyAdmin** or any MySQL client.
   - Create a new database named `user_database`.
   - Create a table `users` with the following structure:
     | Column      | Type         | Extra          |
     |-------------|--------------|----------------|
     | `id`        | INT          | AUTO_INCREMENT PRIMARY KEY |
     | `username`  | VARCHAR(255) | NOT NULL       |
     | `email`     | VARCHAR(255) | UNIQUE NOT NULL |
     | `password`  | VARCHAR(255) | NOT NULL       |

4. **Update `config.php`:**
   - Ensure the `config.php` file has the correct database credentials:
     ```php
     $host = "localhost";
     $username = "root";
     $password = "";
     $dbname = "user_database";
     ```

5. **Test PHP files:**
   - Start your server (XAMPP/LAMP).
   - Access the PHP files in your browser:
     ```
     http://localhost/myproject/register.php
     http://localhost/myproject/login.php
     ```

---

### 2. Android Setup
1. **Open the project in Android Studio:**
   - Import the project folder into Android Studio.

2. **Update API Base URL:**
   - In the file `ApiService.java`, update the `BASE_URL` to point to your backend server. For example:
     ```java
     private static final String BASE_URL = "http://<your-server-ip>/myproject/";
     ```
   - Replace `<your-server-ip>` with:
     - `10.0.2.2` if using an emulator.
     - Your local IP address (e.g., `192.168.1.100`) if testing on a physical device.

3. **Run the app:**
   - Connect a device or use an emulator.
   - Test the registration and login features.

---

## Testing the Application

### Backend
Use **Postman** or a similar tool to test your PHP endpoints:
1. **Registration Endpoint:**
   - URL: `http://localhost/myproject/register.php`
   - Method: `POST`
   - Body (x-www-form-urlencoded):
     - `username`: `testuser`
     - `email`: `test@example.com`
     - `password`: `password123`

2. **Login Endpoint:**
   - URL: `http://localhost/myproject/login.php`
   - Method: `POST`
   - Body (x-www-form-urlencoded):
     - `email`: `test@example.com`
     - `password`: `password123`

### Android App
1. Open the app.
2. Use the registration form to register a new user.
3. Test login functionality with the registered user credentials.

---

## Project Structure

### Backend
- `php/config.php`: Handles database connection.
- `php/register.php`: API for user registration.
- `php/login.php`: API for user login.

### Android
- `ApiService.java`: Retrofit setup for API communication.
- `UserApi.java`: Defines API endpoints.
- `MainActivity.java`: Handles user registration.
- `LoginActivity.java`: Handles user login.
- `activity_main.xml`: Layout for registration screen.
- `activity_login.xml`: Layout for login screen.

---

## Troubleshooting

1. **Database connection issues:**
   - Ensure your `config.php` has the correct credentials.
   - Verify that MySQL is running on your server.

2. **404 Not Found error:**
   - Ensure PHP files are in the correct directory (e.g., `/opt/lampp/htdocs/myproject`).

3. **API communication errors:**
   - Verify the `BASE_URL` in `ApiService.java` points to the correct server.

---

## Contributing

Feel free to contribute to the project! Open issues or submit pull requests to suggest improvements or fix bugs.

---

## License

This project is licensed under the MIT License. You are free to use, modify, and distribute this project.

```
