document.addEventListener('DOMContentLoaded', () => {
    const userRole = sessionStorage.getItem('userRole');
    if (userRole && userRole !== 'ADMINISTRADOR') {
         document.body.classList.add('rol-solo-ver');
    }
    const form = document.getElementById('entidad-form');
    const tablaBody = document.getElementById('entidades-tabla-body');
    const inputId = document.getElementById('entidad-id');
    const inputNombre = document.getElementById('nombre');
    const inputDomicilio = document.getElementById('domicilioCentral');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarEntidades() { 
        try {
            const response = await fetch('/api/entidades'); 
            
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar las entidades.'); 
            }

            const entidades = await response.json(); 
            tablaBody.innerHTML = ''; 

            entidades.forEach(entidad => {
                const tr = document.createElement('tr');

                tr.innerHTML = `
                    <td>${entidad.nombre}</td>
                    <td>${entidad.domicilioCentral}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${entidad.id}" 
                                data-nombre="${entidad.nombre}"
                                data-domicilio="${entidad.domicilioCentral}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${entidad.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando entidades:', error); 
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputNombre.value = btn.dataset.nombre;
        inputDomicilio.value = btn.dataset.domicilio; 
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
    }
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;

        const entidadData = {
            nombre: inputNombre.value,
            domicilioCentral: inputDomicilio.value
        };

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/entidades' : `/api/entidades/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(entidadData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar la entidad.');
            }

            alert('Entidad guardada exitosamente.');
            limpiarFormulario();
            await cargarEntidades(); 

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
            
            if (!confirm(`¿Está seguro de que desea eliminar la entidad con ID ${id}?`)) {
                return;
            }

            try {
                const response = await fetch(`/api/entidades/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar la entidad.'); 
                }

                alert('Entidad eliminada.'); 
                await cargarEntidades();

            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);
    cargarEntidades();
});