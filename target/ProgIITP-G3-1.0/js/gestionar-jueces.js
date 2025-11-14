document.addEventListener('DOMContentLoaded', () => {
    const userRole = sessionStorage.getItem('userRole');
    if (userRole && userRole !== 'ADMINISTRADOR') {
         document.body.classList.add('rol-solo-ver');
    }
    const form = document.getElementById('juez-form');
    const tablaBody = document.getElementById('jueces-tabla-body');
    const inputId = document.getElementById('juez-id');
    const inputNombre = document.getElementById('nombre');
    const inputAnios = document.getElementById('aniosDeServicio');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarJueces() {
        try {
            const response = await fetch('/api/jueces');

            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar los jueces.');
            }

            const jueces = await response.json();
            tablaBody.innerHTML = '';

            jueces.forEach(juez => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${juez.nombre}</td>
                    <td>${juez.aniosDeServicio}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${juez.clave}" 
                                data-nombre="${juez.nombre}"
                                data-anios="${juez.aniosDeServicio}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${juez.clave}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando jueces:', error);
        }
    }
    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputNombre.value = btn.dataset.nombre;
        inputAnios.value = btn.dataset.anios;
        window.scrollTo(0, 0);
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = '';
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const id = inputId.value;
        const juezData = {
            nombre: inputNombre.value,
            aniosDeServicio: parseInt(inputAnios.value)
        };

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';

        const url = esNuevo ? '/api/jueces' : `/api/jueces/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(juezData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar el juez.');
            }

            alert('Juez guardado exitosamente.');
            limpiarFormulario();
            await cargarJueces();

        } catch (error) {
            console.error('Error guardando:', error);
            alert(`Error: ${error.message}`);
        }
    });

    tablaBody.addEventListener('click', async (e) => {

        if (e.target.classList.contains('btn-edit')) {
            poblarFormulario(e.target);
        }

        if (e.target.classList.contains('btn-delete')) {
            const id = e.target.dataset.id;

            if (!confirm(`¿Está seguro de que desea eliminar al juez con clave ${id}?`)) {
                return;
            }

            try {
                const response = await fetch(`/api/jueces/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar el juez.');
                }

                alert('Juez eliminado.');
                await cargarJueces();

            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);

    cargarJueces();
});