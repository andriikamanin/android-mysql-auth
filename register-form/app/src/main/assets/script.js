document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Предотвращаем стандартное поведение формы

    const formData = new FormData(this);
    const data = {
        username: formData.get('firstname') + ' ' + formData.get('lastname'),
        email: formData.get('email'),
        password: formData.get('password'),
        confirmPassword: formData.get('confirmPassword')
    };

    // Проверка совпадения паролей
    if (data.password !== data.confirmPassword) {
        alert('Passwords do not match!');
        return;
    }

    try {
        // Отправляем данные на сервер через fetch
        const response = await fetch('https://your-server-url/register.php', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: data.username,
                email: data.email,
                password: data.password
            })
        });

        const result = await response.json();
        if (result.success) {
            alert(result.message);
            this.reset(); // Сброс формы
        } else {
            alert('Error: ' + result.message);
        }
    } catch (err) {
        alert('Request failed: ' + err.message);
    }
});
