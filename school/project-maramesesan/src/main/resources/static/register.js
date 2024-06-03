fetch('/api/auth/sign-up', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        username: 'exampleUser',
        email: 'user@example.com',
        password: 'password123'
    })
})
    .then(response => {
        if (response.status === 302) {
            // Redirect to the location provided in the response
            window.location.href = response.headers.get('Location');
        } else {
            return response.json(); // Return JSON if not redirected
        }
    })
    .then(data => {
        if (data) {
            // Handle the response JSON if needed
            console.log(data);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
