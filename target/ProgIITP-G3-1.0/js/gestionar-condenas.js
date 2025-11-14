document.addEventListener('DOMContentLoaded', () => {
    const userRole = sessionStorage.getItem('userRole');
    if (userRole && userRole !== 'ADMINISTRADOR') {
         document.body.classList.add('rol-solo-ver');
    }
    const form = document.getElementById('condena-form');
    const tablaBody = document.getElementById('condenas-tabla-body');
    const inputId = document.getElementById('condena-id');
    const selectRobo = document.getElementById('roboId');
    const inputFechaInicio = document.getElementById('fechaInicio');
    const inputFechaFin = document.getElementById('fechaFin');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarRobosDropdown() {
        try {
            const response = await fetch('/api/robos');
            if (!response.ok) {
                throw new Error('No se pudieron cargar los robos.');
            }
            const robos = await response.json();
            selectRobo.innerHTML = '<option value="">-- Seleccione un robo --</option>';
            robos.forEach(robo => {
                const option = document.createElement('option');
                option.value = robo.id;
                option.textContent = `ID Robo: ${robo.id} (Fecha: ${robo.fechaRobo})`;
                selectRobo.appendChild(option);
            });
        } catch (error) {
            console.error('Error cargando robos:', error);
            selectRobo.innerHTML = '<option value="">Error al cargar</option>';
        }
    }

    async function cargarCondenas() {
        try {
            const response = await fetch('/api/condenas');
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar las condenas.');
            }
            const condenas = await response.json();
            tablaBody.innerHTML = ''; 
            condenas.forEach(condena => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${condena.idRobo}</td>
                    <td>${condena.fechaInicio}</td>
                    <td>${condena.fechaFin}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${condena.id}" 
                                data-roboid="${condena.idRobo}"
                                data-inicio="${condena.fechaInicio}"
                                data-fin="${condena.fechaFin}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${condena.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });
        } catch (error) {
            console.error('Error cargando condenas:', error);
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        selectRobo.value = btn.dataset.roboid;
        inputFechaInicio.value = btn.dataset.inicio;
        inputFechaFin.value = btn.dataset.fin;

        selectRobo.disabled = true; 
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
        selectRobo.disabled = false; 
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;
        
        const condenaData = {
            idRobo: parseInt(selectRobo.value),
            fechaInicio: inputFechaInicio.value,
            fechaFin: inputFechaFin.value
        };

        if (!condenaData.idRobo) {
            alert('Debe seleccionar un robo.');
            return;
        }

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/condenas' : `/api/condenas/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(condenaData)
            });
            if (!response.ok) {
                const error = await response.text();
                if (error.includes("ya tiene una condena")) {
                     alert(`Error: ${error}`);
                } else {
                    throw new Error(error || 'Error al guardar la condena.');
                }
            } else {
                alert('Condena guardada exitosamente.');
                limpiarFormulario();
                await cargarCondenas();
                await cargarRobosDropdown(); 
            }
        } catch (error) {
            console.error('Error guardando:', error);
             if (!alert.toString().includes('Error:')) {
                alert(`Error: ${error.message}`);
             }
        }
    });

    tablaBody.addEventListener('click', async (e) => {
        if (e.target.classList.contains('btn-edit')) {
            poblarFormulario(e.target);
        }
        if (e.target.classList.contains('btn-delete')) {
            const id = e.target.dataset.id;
            if (!confirm(`¿Está seguro de que desea eliminar la condena con ID ${id}?`)) {
                return;
            }
            try {
                const response = await fetch(`/api/condenas/${id}`, {
                    method: 'DELETE'
                });
                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar la condena.');
                }
                alert('Condena eliminada.');
                await cargarCondenas();
                await cargarRobosDropdown();
            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);
    cargarRobosDropdown();
    cargarCondenas();
});