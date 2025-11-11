document.addEventListener('DOMContentLoaded', () => {

    const form = document.getElementById('banda-form');
    const tablaBody = document.getElementById('bandas-tabla-body');
    const inputId = document.getElementById('banda-id');
    const inputNombre = document.getElementById('nombre');
    const inputCantMiembros = document.getElementById('cantMiembros');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarBandas() {
        try {
            const response = await fetch('/api/bandas');
            
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar las bandas.');
            }

            const bandas = await response.json(); 
            tablaBody.innerHTML = ''; 

            bandas.forEach(banda => {
                const tr = document.createElement('tr');
         
                tr.innerHTML = `
                    <td>${banda.nombre}</td>
                    <td>${banda.cantMiembros}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${banda.id}" 
                                data-nombre="${banda.nombre}"
                                data-miembros="${banda.cantMiembros}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${banda.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando bandas:', error); 
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputNombre.value = btn.dataset.nombre;
        inputCantMiembros.value = btn.dataset.miembros;
        window.scrollTo(0, 0); 
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = inputId.value;

        const bandaData = {
            nombre: inputNombre.value,
            cantMiembros: parseInt(inputCantMiembros.value)
        };

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/bandas' : `/api/bandas/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(bandaData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar la banda.');
            }

            alert('Banda guardada exitosamente.'); 
            limpiarFormulario();
            await cargarBandas();

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
            
            if (!confirm(`¿Está seguro de que desea eliminar la banda con ID ${id}?`)) { 
                return;
            }

            try {
                const response = await fetch(`/api/bandas/${id}`, { 
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar la banda.'); 
                }

                alert('Banda eliminada.'); 
                await cargarBandas(); 

            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);
    cargarBandas();
});