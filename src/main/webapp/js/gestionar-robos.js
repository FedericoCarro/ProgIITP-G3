document.addEventListener('DOMContentLoaded', () => {

    const form = document.getElementById('robo-form');
    const tablaBody = document.getElementById('robos-tabla-body');
    const inputId = document.getElementById('robo-id');
    const selectDetenido = document.getElementById('detenidoId');
    const selectSucursal = document.getElementById('sucursalId');
    const selectJuez = document.getElementById('juezId');
    const inputFechaRobo = document.getElementById('fechaRobo');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarDetenidosDropdown() {
        try {
            const response = await fetch('/api/detenidos');
            if (!response.ok) {
                throw new Error('No se pudieron cargar los detenidos.');
            }
            const detenidos = await response.json();
            selectDetenido.innerHTML = '<option value="">-- Seleccione un detenido --</option>';
            detenidos.forEach(d => {
                const option = document.createElement('option');
                option.value = d.id;
                option.textContent = `${d.id} - ${d.nomApe}`;
                selectDetenido.appendChild(option);
            });
        } catch (error) {
            console.error('Error cargando detenidos:', error);
            selectDetenido.innerHTML = '<option value="">Error al cargar</option>';
        }
    }

    async function cargarSucursalesDropdown() {
        try {
            const response = await fetch('/api/sucursales');
            if (!response.ok) {
                throw new Error('No se pudieron cargar las sucursales.');
            }
            const sucursales = await response.json();
            selectSucursal.innerHTML = '<option value="">-- Seleccione una sucursal --</option>';
            sucursales.forEach(s => {
                const option = document.createElement('option');
                option.value = s.id;
                option.textContent = `${s.id} - ${s.domicilio}`;
                selectSucursal.appendChild(option);
            });
        } catch (error) {
            console.error('Error cargando sucursales:', error);
            selectSucursal.innerHTML = '<option value="">Error al cargar</option>';
        }
    }

    async function cargarJuecesDropdown() {
        try {
            const response = await fetch('/api/jueces');
            if (!response.ok) {
                throw new Error('No se pudieron cargar los jueces.');
            }
            const jueces = await response.json();
            selectJuez.innerHTML = '<option value="">-- Seleccione un juez --</option>';
            jueces.forEach(j => {
                const option = document.createElement('option');
                option.value = j.clave; // El ID del juez es "clave"
                option.textContent = `${j.clave} - ${j.nombre}`;
                selectJuez.appendChild(option);
            });
        } catch (error) {
            console.error('Error cargando jueces:', error);
            selectJuez.innerHTML = '<option value="">Error al cargar</option>';
        }
    }

    async function cargarRobos() {
        try {
            const response = await fetch('/api/robos');
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar los robos.');
            }
            const robos = await response.json();
            tablaBody.innerHTML = ''; 
            robos.forEach(robo => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${robo.idDetenido}</td>
                    <td>${robo.idSucursal}</td>
                    <td>${robo.idJuez}</td>
                    <td>${robo.fechaRobo}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${robo.id}" 
                                data-detenidoid="${robo.idDetenido}"
                                data-sucursalid="${robo.idSucursal}"
                                data-juezid="${robo.idJuez}"
                                data-fecha="${robo.fechaRobo}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${robo.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });
        } catch (error) {
            console.error('Error cargando robos:', error);
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        selectDetenido.value = btn.dataset.detenidoid;
        selectSucursal.value = btn.dataset.sucursalid;
        selectJuez.value = btn.dataset.juezid;
        inputFechaRobo.value = btn.dataset.fecha;
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;
        
        const roboData = {
            idDetenido: parseInt(selectDetenido.value),
            idSucursal: parseInt(selectSucursal.value),
            idJuez: parseInt(selectJuez.value),
            fechaRobo: inputFechaRobo.value
        };

        if (!roboData.idDetenido || !roboData.idSucursal || !roboData.idJuez) {
            alert('Debe completar todos los campos.');
            return;
        }

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/robos' : `/api/robos/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(roboData)
            });
            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar el robo.');
            }
            alert('Robo guardado exitosamente.');
            limpiarFormulario();
            await cargarRobos();
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
            if (!confirm(`¿Está seguro de que desea eliminar el robo con ID ${id}?`)) {
                return;
            }
            try {
                const response = await fetch(`/api/robos/${id}`, {
                    method: 'DELETE'
                });
                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar el robo.');
                }
                alert('Robo eliminado.');
                await cargarRobos();
            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });
    btnCancelar.addEventListener('click', limpiarFormulario);
    cargarDetenidosDropdown();
    cargarSucursalesDropdown();
    cargarJuecesDropdown();
    cargarRobos();
});