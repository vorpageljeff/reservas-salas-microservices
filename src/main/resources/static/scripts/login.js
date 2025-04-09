const onOpenLogin = () => {
    const form = document.querySelector('#login');
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        login();
    });
}

const login = async () => {
    const login = document.querySelector('#user').value;
    const password = document.querySelector('#password').value;

    try {
        const response = await fetch(`${URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ login, password })
        })

        if (response.ok) {
            const message = await response.text();
            toastAlert(message, 'success');
            isAuthenticated = true;
            sessionStorage.setItem('auth', isAuthenticated)
            getMainFrameContent('ranking');
        } else {
            const errorMessage = await response.text();
            isAuthenticated = false;
            sessionStorage.setItem('auth', isAuthenticated)
            toastAlert(errorMessage, 'error');
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
    }
}