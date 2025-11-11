document.addEventListener('DOMContentLoaded', () => {

    const form = document.getElementById('sucursal-form');
    const tablaBody = document.getElementById('sucursales-tabla-body'); 
    const inputId = document.getElementById('sucursal-id'); 
    const inputDomicilio = document.getElementById('domicilio');
    const inputCantEmpleados = document.getElementById('cantEmpleados');
    const selectEntidad = document.getElementById('entidadBancariaId');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarEntidadesDropdown() {
        try {
            const response = await fetch('/api/entidades');
            if (!response.ok) {
                throw new Error('No se pudieron cargar las entidades para el formulario.');
            }
            const entidades = await response.json();

            selectEntidad.innerHTML = '<option value="">-- Seleccione una entidad --</option>';
            
            entidades.forEach(entidad => {
                const option = document.createElement('option');
                option.value = entidad.id;
                option.textContent = `${entidad.id} - ${entidad.nombre}`;
                selectEntidad.appendChild(option);
            });

        } catch (error) {
            console.error('Error cargando entidades:', error);
            selectEntidad.innerHTML = '<option value="">Error al cargar entidades</option>';
        }
    }

    async function cargarSucursales() { 
        try {
            const response = await fetch('/api/sucursales');
            
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar las sucursales.');
            }

            const sucursales = await response.json(); 
            tablaBody.innerHTML = ''; 

            sucursales.forEach(sucursal => { 
                const tr = document.createElement('tr');

                tr.innerHTML = `
                    <td>${sucursal.domicilio}</td>
                    <td>${sucursal.cantEmpleados}</td>
                    <td>${sucursal.idEntidadBancaria}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${sucursal.id}" 
                                data-domicilio="${sucursal.domicilio}"
                                data-empleados="${sucursal.cantEmpleados}"
                                data-entidadid="${sucursal.idEntidadBancaria}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${sucursal.id}">
                            Eliminar
                        </button>
                    </td>
                `;

                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando sucursales:', error);
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputDomicilio.value = btn.dataset.domicilio; 
        inputCantEmpleados.value = btn.dataset.empleados;
        selectEntidad.value = btn.dataset.entidadid;
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
        selectEntidad.value = '';
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;

        const sucursalData = {
            domicilio: inputDomicilio.value,
            cantEmpleados: parseInt(inputCantEmpleados.value),
            idEntidadBancaria: parseInt(selectEntidad.value)
        };

        if (!sucursalData.idEntidadBancaria) {
            alert('Por favor, seleccione una entidad bancaria.');
            return;
        }

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/sucursales' : `/api/sucursales/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(sucursalData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar la sucursal.');
            }

            alert('Sucursal guardada exitosamente.');
            limpiarFormulario();
            await cargarSucursales();

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
            
            if (!confirm(`¿Está seguro de que desea eliminar la sucursal con ID ${id}?`)) {
                return;
            }

            try {
                const response = await fetch(`/api/sucursales/${id}`, { 
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const error = await response.text();

                    if (error.includes("robos asociados") || error.includes("contratos de vigilantes")) {
                        alert(`Error: No se puede borrar la sucursal (ID: ${id}) porque tiene robos o contratos asociados.`);
                    } else {
                        throw new Error(error || 'Error al eliminar la sucursal.');
                    }
                } else {
                    alert('Sucursal eliminada.'); 
                    await cargarSucursales();
                }
            } catch (error) {
                console.error('Error eliminando:', error);
                if (!alert.toString().includes('Error:')) { 
                    alert(`Error: ${error.message}`);
                }
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);

    cargarEntidadesDropdown(); 
    cargarSucursales();
});