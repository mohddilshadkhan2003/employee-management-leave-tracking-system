document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const messageBox = document.getElementById('message');

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        const result = await response.json();

        if (response.ok && result.success) {
            localStorage.setItem('role', result.role);
            localStorage.setItem('username', result.username);
            messageBox.textContent = result.message;
            messageBox.classList.add('success');

            if (result.role === 'ADMIN') {
                setTimeout(() => {
                    window.location.href = '/admin.html';
                }, 600);
            } else {
                setTimeout(() => {
                    window.location.href = '/employee.html';
                }, 600);
            }
        } else {
            messageBox.textContent = result.message || 'Login failed';
            messageBox.classList.remove('success');
        }
    } catch (error) {
        messageBox.textContent = 'Unable to connect to server';
        messageBox.classList.remove('success');
    }
});
