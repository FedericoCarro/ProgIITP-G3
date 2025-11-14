(async function () {

    try {
        const response = await fetch('/api/usuarios/mi-perfil', {
            method: 'GET'
        });

        if (response.ok) {
            const usuario = await response.json();

            document.getElementById('bienvenida').textContent = `Bienvenido, ${usuario.nomApe}`;
            sessionStorage.setItem('userRole',usuario.permiso);
            
            construirMenu(usuario.permiso);

        } else if (response.status === 401 || response.status === 403) {
            sessionStorage.removeItem('userRole');
            window.location.href = '/vistas/login.html';
        }

    } catch (error) {
        sessionStorage.removeItem('userRole');
        console.error('Error:', error);
        window.location.href = '/vistas/login.html';
    }
})();

function construirMenu(permiso) {
    const menuContainer = document.getElementById('menu-container');
    let menuHtml = '<ul>';

    menuHtml += '<li><a href="#">Inicio</a></li>';

    switch (permiso) {
        case 'ADMINISTRADOR':
            menuHtml += '<h3>Gestión General</h3>';
            menuHtml += '<li><a href="gestionar-usuarios.html">Gestionar Usuarios</a></li>';
            menuHtml += '<li><a href="gestionar-entidades.html">Gestionar Entidades</a></li>';
            menuHtml += '<li><a href="gestionar-sucursales.html">Gestionar Sucursales</a></li>';
            menuHtml += '<li><a href="gestionar-contratos.html">Gestionar Contratos</a></li>';
            menuHtml += '<h3>Gestión de Casos</h3>';
            menuHtml += '<li><a href="gestionar-bandas.html">Gestionar Bandas</a></li>';
            menuHtml += '<li><a href="gestionar-detenidos.html">Gestionar Detenidos</a></li>';
            menuHtml += '<li><a href="gestionar-jueces.html">Gestionar Jueces</a></li>';
            menuHtml += '<li><a href="gestionar-robos.html">Gestionar Robos</a></li>';
            menuHtml += '<li><a href="gestionar-condenas.html">Gestionar Condenas</a></li>';
            break;

        case 'INVESTIGADOR':
            menuHtml += '<h3>Menú Investigador</h3>';
            menuHtml += '<li><a href="gestionar-bandas.html">Ver Bandas</a></li>';
            menuHtml += '<li><a href="gestionar-detenidos.html">Ver Detenidos</a></li>';
            menuHtml += '<li><a href="gestionar-robos.html">Ver Robos</a></li>';
            menuHtml += '<li><a href="gestionar-jueces.html">Ver Jueces</a></li>';
            menuHtml += '<li><a href="gestionar-sucursales.html">Ver Sucursales</a></li>';
            menuHtml += '<li><a href="gestionar-contratos.html">Ver Contratos</a></li>';
            break;

        case 'VIGILANTE':
            menuHtml += '<h3>Menú Vigilante</h3>';
            menuHtml += '<li><a href="mis-contratos.html">Ver mis Contratos</a></li>';
            break;
            
        default:
             menuHtml += '<li>Permiso no reconocido.</li>';
    }

    menuHtml += '</ul>';
    menuContainer.innerHTML = menuHtml;
}
document.getElementById('logout-btn').addEventListener('click', async () => {
        try {
        await fetch('/api/auth/logout', { 
            method: 'POST' 
        });
    } catch (error) {
        console.error("Error en logout:", error);
    }
    sessionStorage.removeItem('userRole');
    window.location.href = '/vistas/login.html';
});