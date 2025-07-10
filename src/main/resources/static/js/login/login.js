$.ajax({
    url: '/api/auth/login-page', // e.g., '/api/data'
    type: 'POST', // or 'POST', depending on your API
    headers: {
        'Authorization': 'Bearer ' + tokenValue // Assuming token is got from /login
    },
    success: function (response) {
        console.log('Data received:', response);
        // Handle the response data here
    },
    error: function (xhr, status, error) {
        console.error('Error:', error);
        if (xhr.status === 401) {
            alert('Unauthorized: Please log in again.');
            // Optionally redirect to login page
        }
    }
});