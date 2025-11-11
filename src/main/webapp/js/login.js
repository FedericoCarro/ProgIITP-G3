document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault(); 

    const dni = document.getElementById('dni').value;
    const contrasenia = document.getElementById('contrasenia').value;
    const errorMessage = document.getElementById('error-message');
  
    const loginData = {
        dni: dni,
        contrasenia: contrasenia
    };

    try {

        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {

            const data = await response.json();

            window.location.href = '/vistas/dashboard.html';

        } else {

            errorMessage.textContent = 'DNI o contraseña incorrectos.';
        }
    } catch (error) {
        console.error('Error de red:', error);
        errorMessage.textContent = 'Error de conexión con el servidor.';
    }
});