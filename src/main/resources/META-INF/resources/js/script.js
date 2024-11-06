const apiUrl = 'http://localhost:8080/api/v1/user'; // Change this URL based on your backend

document.getElementById('loginForm')?.addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const response = await fetch(`http://localhost:8080/security/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userName: username, hashedPassword: password }),
    });

    const messageDiv = document.getElementById('loginMessage');
    
    if (response.ok) {
        const data = await response.json();
        sessionStorage.setItem('token', data.token);
        window.location.href = 'home.html';
        // Redirect to user dashboard or home page
    } else {
        messageDiv.innerText = 'Login failed. Please check your credentials.';
    }
});

document.getElementById('registerForm')?.addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userName: username, hashedPassword: password }),
    });

    const messageDiv = document.getElementById('registerMessage');

    if (response.ok) {
        const data = await response.json();
        sessionStorage.setItem('userName', data.userName);
        messageDiv.innerText = `User  ${data.userName} registered successfully!`;
        // Optionally redirect to login page
    } else {
        messageDiv.innerText = 'Registration failed. Please try again.';
    }
});