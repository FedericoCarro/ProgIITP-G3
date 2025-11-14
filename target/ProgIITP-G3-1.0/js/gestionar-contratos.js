document.addEventListener('DOMContentLoaded', () => {
    const userRole = sessionStorage.getItem('userRole');
    if (userRole && userRole !== 'ADMINISTRADOR') {
         document.body.classList.add('rol-solo-ver');
    }
    const form = document.getElementById('contrato-form');
    const tablaBody = document.getElementById('contratos-tabla-body');
    const inputId = document.getElementById('contrato-id');
    const selectVigilante = document.getElementById('vigilanteId');
    const selectSucursal = document.getElementById('sucursalId');
    const inputHoras = document.getElementById('horasTrabajadas');
    const inputFechaInicio = document.getElementById('fechaInicio');
    const inputFechaFin = document.getElementById('fechaFin');
    const checkConArma = document.getElementById('conArma');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarVigilantesDropdown() {
        try {
            const response = await fetch('/api/usuarios');
            if (!response.ok) {
                throw new Error('No se pudieron cargar los usuarios.');
            }
            const usuarios = await response.json();

            const vigilantes = usuarios.filter(u => u.permiso === 'VIGILANTE');

            selectVigilante.innerHTML = '<option value="">-- Seleccione un vigilante --</option>';
            vigilantes.forEach(v => {
                const option = document.createElement('option');
                option.value = v.id;
                option.textContent = `${v.id} - ${v.nomApe} (DNI: ${v.dni})`;
                selectVigilante.appendChild(option);
            });
        } catch (error) {
            console.error('Error cargando vigilantes:', error);
            selectVigilante.innerHTML = '<option value="">Error al cargar vigilantes</option>';
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
            selectSucursal.innerHTML = '<option value="">Error al cargar sucursales</option>';
        }
    }

    async function cargarContratos() {
        try {
            const response = await fetch('/api/contratos');
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar los contratos.');
            }
            const contratos = await response.json();
            tablaBody.innerHTML = '';

            contratos.forEach(c => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${c.idVigilante}</td>
                    <td>${c.idSucBancaria}</td>
                    <td>${c.conArma ? 'Sí' : 'No'}</td>
                    <td>${c.horasTrabajadas}</td>
                    <td>${c.fechaInicioJornada}</td>
                    <td>${c.fechaFinJornada}</td>
                    <td>
                        <button class="btn-delete" data-id="${c.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });
        } catch (error) {
            console.error('Error cargando contratos:', error);
        }
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = '';
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const contratoData = {
            idVigilante: parseInt(selectVigilante.value),
            idSucBancaria: parseInt(selectSucursal.value),
            horasTrabajadas: parseInt(inputHoras.value),
            fechaInicioJornada: inputFechaInicio.value,
            fechaFinJornada: inputFechaFin.value,
            conArma: checkConArma.checked
        };

        if (!contratoData.idVigilante || !contratoData.idSucBancaria) {
            alert('Debe seleccionar un vigilante y una sucursal.');
            return;
        }
        const method = 'POST';
        const url = '/api/contratos';

        try {
            const response = await fetch(url, {
                method: method,
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(contratoData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar el contrato.');
            }

            alert('Contrato guardado exitosamente.');
            limpiarFormulario();
            await cargarContratos();

        } catch (error) {
            console.error('Error guardando:', error);
            alert(`Error: ${error.message}`);
        }
    });

    tablaBody.addEventListener('click', async (e) => {
        if (e.target.classList.contains('btn-delete')) {
            const id = e.target.dataset.id;
            if (!confirm(`¿Está seguro de que desea eliminar el contrato con ID ${id}?`)) {
                return;
            }
            try {
                const response = await fetch(`/api/contratos/${id}`, {
                    method: 'DELETE'
                });
                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar el contrato.');
                }
                alert('Contrato eliminado.');
                await cargarContratos();
            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);
    cargarVigilantesDropdown();
    cargarSucursalesDropdown();
    cargarContratos();
});