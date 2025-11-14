
document.addEventListener('DOMContentLoaded', () => {
    const userRole = sessionStorage.getItem('userRole');
    if (userRole && userRole !== 'ADMINISTRADOR') {
         document.body.classList.add('rol-solo-ver');
    }
    const form = document.getElementById('usuario-form');
    const tablaBody = document.getElementById('usuarios-tabla-body');
    const inputId = document.getElementById('usuario-id');
    const inputNomApe = document.getElementById('nomApe');
    const inputDni = document.getElementById('dni');
    const inputTel = document.getElementById('tel');
    const inputEdad = document.getElementById('edad');
    const inputContrasenia = document.getElementById('contrasenia');
    const selectPermiso = document.getElementById('permiso');
    const btnCancelar = document.getElementById('btn-cancelar');

    async function cargarUsuarios() {
        try {
            const response = await fetch('/api/usuarios');
            
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/vistas/login.html';
            }
            if (!response.ok) {
                throw new Error('No se pudieron cargar los usuarios.');
            }

            const usuarios = await response.json();
            tablaBody.innerHTML = ''; 

            usuarios.forEach(usuario => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${usuario.nomApe}</td>
                    <td>${usuario.dni}</td>
                    <td>${usuario.tel}</td>
                    <td>${usuario.permiso}</td>
                    <td>
                        <button class="btn-edit" 
                                data-id="${usuario.id}" 
                                data-nomape="${usuario.nomApe}"
                                data-dni="${usuario.dni}"
                                data-tel="${usuario.tel}"
                                data-edad="${usuario.edad}"
                                data-permiso="${usuario.permiso}">
                            Editar
                        </button>
                        <button class="btn-delete" data-id="${usuario.id}">
                            Eliminar
                        </button>
                    </td>
                `;
                tablaBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error cargando usuarios:', error);
        }
    }

    function poblarFormulario(btn) {
        inputId.value = btn.dataset.id;
        inputNomApe.value = btn.dataset.nomape;
        inputDni.value = btn.dataset.dni;
        inputTel.value = btn.dataset.tel;
        inputEdad.value = btn.dataset.edad;
        selectPermiso.value = btn.dataset.permiso;
        window.scrollTo(0, 0);
    }

    function limpiarFormulario() {
        form.reset();
        inputId.value = ''; 
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const id = inputId.value;
        

        const usuarioData = {
            nomApe: inputNomApe.value,
            dni: parseInt(inputDni.value),
            tel: parseInt(inputTel.value),
            edad: parseInt(inputEdad.value),
            contrasenia: inputContrasenia.value,
            permiso: selectPermiso.value
        };

        if (!usuarioData.contrasenia) {
            delete usuarioData.contrasenia;
        }

        const esNuevo = !id;
        const method = esNuevo ? 'POST' : 'PUT';
        const url = esNuevo ? '/api/usuarios' : `/api/usuarios/${id}`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usuarioData)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error || 'Error al guardar el usuario.');
            }

            alert('Usuario guardado exitosamente.');
            limpiarFormulario();
            await cargarUsuarios(); 

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
            
            if (!confirm(`¿Está seguro de que desea eliminar al usuario con ID ${id}?`)) {
                return;
            }

            try {
                const response = await fetch(`/api/usuarios/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error || 'Error al eliminar el usuario.');
                }

                alert('Usuario eliminado.');
                await cargarUsuarios();

            } catch (error) {
                console.error('Error eliminando:', error);
                alert(`Error: ${error.message}`);
            }
        }
    });

    btnCancelar.addEventListener('click', limpiarFormulario);

    cargarUsuarios();
});