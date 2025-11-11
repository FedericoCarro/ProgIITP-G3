document.addEventListener('DOMContentLoaded', () => {

    const form = document.getElementById('detenido-form');
    const tablaBody = document.getElementById('detenidos-tabla-body');
    const inputId = document.getElementById('detenido-id');
    const inputNomApe = document.getElementById('nomApe');
    const selectBanda = document.getElementById('bandaId');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarBandasDropdown() {
        try {
            const response = await fetch('/api/bandas');
            if (!response.ok) {
                throw new Error('No se pudieron cargar las bandas para el formulario.');
            }
            const bandas = await response.json();
            
            selectBanda.innerHTML = '<option value="">-- Seleccione una banda --</option>';
            
            bandas.forEach(banda => {
                const option = document.createElement('option');
                option.value = banda.id;
                option.textContent = `${banda.id} - ${banda.nombre}`;
                selectBanda.appendChild(option);
            });

        } catch (error) {
            console.error('Error cargando bandas:', error);
            selectBanda.innerHTML = '<option value="">Error al cargar bandas</option>';
        }
    }

    async function cargarDetenidos() {
        try {
            const response = await fetch('/api/detenidos'); 
            
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar los detenidos.');
            }

            const detenidos = await response.json();
            tablaBody.innerHTML = ''; 

            detenidos.forEach(detenido => {
                const tr = document.createElement('tr');
                
                tr.innerHTML = `
                    <td>${detenido.nomApe}</td>
                    <td>${detenido.idBO}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${detenido.id}" 
                                data-nomape="${detenido.nomApe}"
                                data-bandaid="${detenido.idBO}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${detenido.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando detenidos:', error);
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputNomApe.value = btn.dataset.nomape;
        selectBanda.value = btn.dataset.bandaid; 
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
        selectBanda.value = '';
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;
        
        const detenidoData = {
            nomApe: inputNomApe.value,
            idBO: parseInt(selectBanda.value) 
        };

        if (!detenidoData.idBO) {
            alert('Por favor, seleccione una banda.');
            return;
        }

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/detenidos' : `/api/detenidos/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(detenidoData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar el detenido.');
            }

            alert('Detenido guardado exitosamente.');
            limpiarFormulario();
            await cargarDetenidos();

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
            if (!confirm(`¿Está seguro de que desea eliminar al detenido con ID ${id}?`)) {
                return;
            }
            try {
                const response = await fetch(`/api/detenidos/${id}`, {
                    method: 'DELETE'
                });
                if (!response.ok) {
                    const error = await response.text();
                    if (error.includes("robos asociados")) { 
                        alert(`Error: No se puede borrar el detenido (ID: ${id}) porque tiene robos asociados.`);
                    } else {
                        throw new Error(error || 'Error al eliminar el detenido.');
                    }
                } else {
                    alert('Detenido eliminado.');
                    await cargarDetenidos();
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
    cargarBandasDropdown();
    cargarDetenidos();
});