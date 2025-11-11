document.addEventListener('DOMContentLoaded', () => {

    const tablaBody = document.getElementById('mis-contratos-tabla-body');

    async function cargarMisContratos() {
        try {

            const response = await fetch('/api/contratos/mis-contratos'); 
            
            if (response.status === 401 || response.status === 403) {

                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar tus contratos.');
            }

            const contratos = await response.json();
            tablaBody.innerHTML = '';

            if (contratos.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="5">No tienes contratos asignados.</td></tr>';
            }

            contratos.forEach(c => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${c.idSucBancaria}</td>
                    <td>${c.conArma ? 'Sí' : 'No'}</td>
                    <td>${c.horasTrabajadas}</td>
                    <td>${c.fechaInicioJornada}</td>
                    <td>${c.fechaFinJornada}</td>
                `;

                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando contratos:', error);
            tablaBody.innerHTML = `<tr><td colspan="5">Error al cargar contratos.</td></tr>`;
        }
    }

    cargarMisContratos();
});