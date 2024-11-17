document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const data = {
        username: formData.get('firstname') + ' ' + formData.get('lastname'),
        email: formData.get('email'),
        password: formData.get('password'),
        confirmPassword: formData.get('confirmPassword')
    };

    if (data.password !== data.confirmPassword) {
        alert('Passwords do not match!');
        return;
    }

    try {
        const response = await fetch('http://api.andkamrootdomain.click/myproject/register.php', {
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
            // Отправляем сообщение об успешной регистрации в Android WebView
            Android.redirectToSuccess(data.username); // Вызов Java-кода из JavaScript
        } else {
            alert('Error: ' + result.message);
        }
    } catch (err) {
        alert('Request failed: ' + err.message);
    }
});
