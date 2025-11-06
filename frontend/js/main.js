/**
 * MetaMapa - JavaScript Principal
 * Funciones para interactividad del cliente web
 */

// ============================================
// UTILIDADES GENERALES
// ============================================

/**
 * Muestra un modal
 */
function abrirModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.classList.add('active');
  }
}

/**
 * Cierra un modal
 */
function cerrarModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.classList.remove('active');
  }
}

/**
 * Muestra una alerta
 */
function mostrarAlerta(tipo, mensaje, elementId = null) {
  let alertElement;

  if (elementId) {
    alertElement = document.getElementById(elementId);
  } else {
    alertElement = document.createElement('div');
    alertElement.className = `alert alert-${tipo}`;
    alertElement.textContent = mensaje;
    document.body.insertBefore(alertElement, document.body.firstChild);

    setTimeout(() => {
      alertElement.remove();
    }, 5000);
    return;
  }

  if (alertElement) {
    alertElement.classList.remove('hidden');
    const messageElement = alertElement.querySelector('[id$="Message"]');
    if (messageElement) {
      messageElement.textContent = mensaje;
    } else {
      alertElement.textContent = mensaje;
    }
  }
}

/**
 * Oculta una alerta
 */
function ocultarAlerta(elementId) {
  const alertElement = document.getElementById(elementId);
  if (alertElement) {
    alertElement.classList.add('hidden');
  }
}

// ============================================
// AUTENTICACIÓN
// ============================================

/**
 * Maneja el envío del formulario de login
 */
function handleLogin(event) {
  event.preventDefault();

  const formData = new FormData(event.target);
  const email = formData.get('email');
  const password = formData.get('password');

  // TODO: Enviar al endpoint /api/auth/login
  console.log('Login:', { email, password });

  // Simulación de login exitoso
  document.getElementById('loginSuccess')?.classList.remove('hidden');
  document.getElementById('loginError')?.classList.add('hidden');

  setTimeout(() => {
    window.location.href = 'index.html';
  }, 1500);

  return false;
}

/**
 * Maneja el envío del formulario de registro
 */
function handleRegistro(event) {
  event.preventDefault();

  const formData = new FormData(event.target);
  const password = formData.get('password');
  const passwordConfirm = formData.get('passwordConfirm');

  // Validar que las contraseñas coincidan
  if (password !== passwordConfirm) {
    mostrarAlerta('error', 'Las contraseñas no coinciden', 'registroError');
    return false;
  }

  const data = {
    nombre: formData.get('nombre'),
    email: formData.get('email'),
    password: password
  };

  // TODO: Enviar al endpoint /api/auth/registro
  console.log('Registro:', data);

  // Simulación de registro exitoso
  document.getElementById('registroSuccess').classList.remove('hidden');
  ocultarAlerta('registroError');

  setTimeout(() => {
    window.location.href = 'login.html';
  }, 2000);

  return false;
}

/**
 * Login con SSO (Keycloak)
 */
function loginConKeycloak() {
  // TODO: Integrar con Keycloak
  console.log('Iniciando login con Keycloak...');
  alert('Integración con Keycloak - Pendiente de implementación en backend');
}

/**
 * Cierra la sesión del usuario
 */
function cerrarSesion() {
  // TODO: Llamar al endpoint /api/auth/logout
  console.log('Cerrando sesión...');
  window.location.href = 'login.html';
}

// ============================================
// FILTROS Y BÚSQUEDA
// ============================================

/**
 * Limpia los filtros del formulario
 */
function limpiarFiltros() {
  const form = document.getElementById('filterForm');
  if (form) {
    form.reset();
    // TODO: Recargar hechos sin filtros
    console.log('Filtros limpiados');
  }
}

/**
 * Aplica filtros a la búsqueda de hechos
 */
function aplicarFiltros(event) {
  if (event) event.preventDefault();

  const form = document.getElementById('filterForm');
  if (form) {
    const formData = new FormData(form);
    const filtros = Object.fromEntries(formData);

    // TODO: Enviar al endpoint /api/hechos/filtrar
    console.log('Aplicando filtros:', filtros);
  }

  return false;
}

// ============================================
// HECHOS
// ============================================

/**
 * Ver detalle de un hecho
 */
function verDetalle(hechoId) {
  // TODO: Cargar datos del hecho desde /api/hechos/{id}
  console.log('Ver detalle del hecho:', hechoId);
  abrirModal('modalDetalle');
}

/**
 * Solicitar eliminación de un hecho
 */
function solicitarEliminacion(hechoId) {
  // TODO: Redirigir a página de solicitud con el ID del hecho
  console.log('Solicitar eliminación del hecho:', hechoId);
  window.location.href = `solicitar-eliminacion.html?hechoId=${hechoId}`;
}

/**
 * Solicitar eliminación desde el modal
 */
function solicitarEliminacionDesdeModal() {
  cerrarModal('modalDetalle');
  // TODO: Obtener ID del hecho actual y redirigir
  window.location.href = 'solicitar-eliminacion.html';
}

/**
 * Maneja la creación de un nuevo hecho
 */
function handleCrearHecho(event) {
  event.preventDefault();

  const formData = new FormData(event.target);

  // Validar coordenadas
  const latitud = parseFloat(formData.get('latitud'));
  const longitud = parseFloat(formData.get('longitud'));

  if (isNaN(latitud) || isNaN(longitud)) {
    mostrarAlerta('error', 'Las coordenadas deben ser números válidos', 'errorAlert');
    return false;
  }

  const data = {
    titulo: formData.get('titulo'),
    descripcion: formData.get('descripcion'),
    categoria: formData.get('categoria'),
    fechaHecho: formData.get('fechaHecho'),
    horaHecho: formData.get('horaHecho'),
    localidad: formData.get('localidad'),
    latitud: latitud,
    longitud: longitud,
    esAnonimo: formData.get('esAnonimo') === 'on',
    // multimedia: formData.get('multimedia') // Enviar como archivo
  };

  // TODO: Enviar al endpoint /api/hechos/crear (con multipart/form-data para el archivo)
  console.log('Crear hecho:', data);

  // Simulación de creación exitosa
  document.getElementById('successAlert').classList.remove('hidden');
  setTimeout(() => {
    window.location.href = 'index.html';
  }, 2000);

  return false;
}

/**
 * Obtiene la ubicación actual del usuario
 */
function obtenerUbicacionActual() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        document.getElementById('latitud').value = position.coords.latitude.toFixed(6);
        document.getElementById('longitud').value = position.coords.longitude.toFixed(6);
        mostrarAlerta('success', '¡Ubicación obtenida exitosamente!');
      },
      (error) => {
        mostrarAlerta('error', 'No se pudo obtener la ubicación: ' + error.message);
      }
    );
  } else {
    mostrarAlerta('error', 'Tu navegador no soporta geolocalización');
  }
}

/**
 * Maneja la selección de archivo multimedia
 */
function handleFileSelect(event) {
  const file = event.target.files[0];
  if (!file) return;

  // Validar tamaño (10MB)
  const maxSize = 10 * 1024 * 1024;
  if (file.size > maxSize) {
    mostrarAlerta('error', 'El archivo no debe superar los 10MB');
    event.target.value = '';
    return;
  }

  // Mostrar preview
  const preview = document.getElementById('filePreview');
  const fileName = document.getElementById('fileName');
  const fileSize = document.getElementById('fileSize');
  const fileIcon = document.getElementById('fileIcon');

  if (preview && fileName && fileSize && fileIcon) {
    fileName.textContent = file.name;
    fileSize.textContent = formatFileSize(file.size);

    // Cambiar icono según el tipo
    if (file.type.startsWith('image/')) {
      fileIcon.textContent = '🖼️';
    } else if (file.type.startsWith('video/')) {
      fileIcon.textContent = '🎥';
    } else {
      fileIcon.textContent = '📄';
    }

    preview.classList.remove('hidden');
  }
}

/**
 * Elimina el archivo seleccionado
 */
function removeFile() {
  const input = document.getElementById('multimedia');
  const preview = document.getElementById('filePreview');

  if (input) input.value = '';
  if (preview) preview.classList.add('hidden');
}

/**
 * Formatea el tamaño del archivo
 */
function formatFileSize(bytes) {
  if (bytes === 0) return '0 Bytes';

  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));

  return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
}

// ============================================
// SOLICITUDES DE ELIMINACIÓN
// ============================================

/**
 * Actualiza el contador de caracteres
 */
function updateCharacterCount() {
  const textarea = document.getElementById('justificacion');
  const counter = document.getElementById('charCount');

  if (textarea && counter) {
    counter.textContent = textarea.value.length;

    if (textarea.value.length >= 500) {
      counter.style.color = 'var(--color-success)';
    } else {
      counter.style.color = 'var(--color-error)';
    }
  }
}

/**
 * Sugiere texto según el motivo seleccionado
 */
function sugerirTexto(motivo) {
  const textarea = document.getElementById('justificacion');
  if (!textarea) return;

  const sugerencias = {
    'informacion_incorrecta': 'Solicito la eliminación de este hecho porque la información presentada contiene inexactitudes. ',
    'duplicado': 'Este hecho es claramente duplicado. Ya existe otro registro en el sistema con el mismo evento, misma fecha y misma ubicación. ',
    'spam': 'Este contenido constituye spam y no aporta valor al sistema. ',
    'no_relevante': 'Este hecho no es relevante para la colección actual porque ',
    'otro': 'Solicito la eliminación de este hecho por el siguiente motivo: '
  };

  if (textarea.value.length < 50) {
    textarea.value = sugerencias[motivo] || '';
    updateCharacterCount();
  }
}

/**
 * Maneja el envío de solicitud de eliminación
 */
function handleSolicitudEliminacion(event) {
  event.preventDefault();

  const formData = new FormData(event.target);
  const justificacion = formData.get('justificacion');

  if (justificacion.length < 500) {
    mostrarAlerta('error', 'La justificación debe tener al menos 500 caracteres', 'errorAlert');
    return false;
  }

  // TODO: Obtener hechoId de la URL
  const urlParams = new URLSearchParams(window.location.search);
  const hechoId = urlParams.get('hechoId');

  const data = {
    hechoId: hechoId,
    justificacion: justificacion
  };

  // TODO: Enviar al endpoint /api/solicitudes-eliminacion/crear
  console.log('Solicitud de eliminación:', data);

  // Simulación de envío exitoso
  document.getElementById('successAlert').classList.remove('hidden');
  setTimeout(() => {
    window.location.href = 'index.html';
  }, 2000);

  return false;
}

// ============================================
// PANEL ADMINISTRATIVO - COLECCIONES
// ============================================

/**
 * Cambia las opciones según el tipo de fuente seleccionado
 */
function cambiarTipoFuente() {
  const select = document.getElementById('tipoFuente');
  if (!select) return;

  const tipo = select.value;

  // Ocultar todas las opciones
  document.getElementById('opcionesFuenteEstatica')?.classList.add('hidden');
  document.getElementById('opcionesFuenteDinamica')?.classList.add('hidden');
  document.getElementById('opcionesFuenteProxy')?.classList.add('hidden');

  // Mostrar las opciones correspondientes
  if (tipo === 'estatica') {
    document.getElementById('opcionesFuenteEstatica')?.classList.remove('hidden');
  } else if (tipo === 'dinamica') {
    document.getElementById('opcionesFuenteDinamica')?.classList.remove('hidden');
  } else if (tipo === 'proxy') {
    document.getElementById('opcionesFuenteProxy')?.classList.remove('hidden');
  }
}

/**
 * Maneja la creación de una colección
 */
function handleCrearColeccion(event) {
  event.preventDefault();

  const formData = new FormData(event.target);

  const data = {
    categoria: formData.get('categoria'),
    localidad: formData.get('localidad'),
    fechaInicial: formData.get('fechaInicial'),
    fechaFinal: formData.get('fechaFinal'),
    tipoFuente: formData.get('tipoFuente'),
    algoritmoConsenso: formData.get('algoritmoConsenso')
  };

  // TODO: Enviar al endpoint /api/colecciones/crear
  console.log('Crear colección:', data);

  cerrarModal('modalCrearColeccion');
  mostrarAlerta('success', '¡Colección creada exitosamente!');

  // Recargar la página después de un breve delay
  setTimeout(() => {
    window.location.reload();
  }, 1500);

  return false;
}

/**
 * Editar una colección
 */
function editarColeccion(coleccionId) {
  // TODO: Cargar datos de la colección y abrir modal de edición
  console.log('Editar colección:', coleccionId);
  alert('Función de edición - Pendiente de implementación');
}

/**
 * Eliminar una colección
 */
function eliminarColeccion(coleccionId) {
  if (confirm('¿Estás seguro de que deseas eliminar esta colección?')) {
    // TODO: Llamar al endpoint /api/colecciones/{id}/eliminar
    console.log('Eliminar colección:', coleccionId);
    mostrarAlerta('success', 'Colección eliminada exitosamente');
  }
}

// ============================================
// PANEL ADMINISTRATIVO - SOLICITUDES
// ============================================

let accionPendiente = null;
let solicitudIdPendiente = null;

/**
 * Aprobar una solicitud de eliminación
 */
function aprobarSolicitud(solicitudId) {
  accionPendiente = 'aprobar';
  solicitudIdPendiente = solicitudId;

  document.getElementById('modalConfirmacionTitulo').textContent = 'Aprobar Solicitud';
  document.getElementById('modalConfirmacionMensaje').textContent =
    '¿Estás seguro de que deseas aprobar esta solicitud de eliminación? El hecho será marcado como eliminado.';
  document.getElementById('modalConfirmacionBtn').className = 'btn btn-success';
  document.getElementById('modalConfirmacionBtn').textContent = 'Aprobar';

  abrirModal('modalConfirmacion');
}

/**
 * Rechazar una solicitud de eliminación
 */
function rechazarSolicitud(solicitudId) {
  accionPendiente = 'rechazar';
  solicitudIdPendiente = solicitudId;

  document.getElementById('modalConfirmacionTitulo').textContent = 'Rechazar Solicitud';
  document.getElementById('modalConfirmacionMensaje').textContent =
    '¿Estás seguro de que deseas rechazar esta solicitud de eliminación?';
  document.getElementById('modalConfirmacionBtn').className = 'btn btn-danger';
  document.getElementById('modalConfirmacionBtn').textContent = 'Rechazar';

  abrirModal('modalConfirmacion');
}

/**
 * Confirma la acción de aprobar/rechazar
 */
function confirmarAccion() {
  if (!accionPendiente || !solicitudIdPendiente) return;

  // TODO: Enviar al endpoint /api/solicitudes-eliminacion/{id}/{accion}
  console.log(`${accionPendiente} solicitud:`, solicitudIdPendiente);

  cerrarModal('modalConfirmacion');
  mostrarAlerta('success', `Solicitud ${accionPendiente === 'aprobar' ? 'aprobada' : 'rechazada'} exitosamente`);

  // Recargar después de un breve delay
  setTimeout(() => {
    window.location.reload();
  }, 1500);

  accionPendiente = null;
  solicitudIdPendiente = null;
}

/**
 * Filtrar solicitudes por estado
 */
function filtrarPorEstado(estado) {
  // TODO: Implementar filtrado real
  console.log('Filtrar por estado:', estado);
}

/**
 * Ver detalle completo de un hecho
 */
function verDetalleHecho(hechoId) {
  // TODO: Cargar y mostrar detalle del hecho
  console.log('Ver detalle del hecho:', hechoId);
  window.open(`../index.html?hechoId=${hechoId}`, '_blank');
}

// ============================================
// PANEL ADMINISTRATIVO - ESTADÍSTICAS
// ============================================

/**
 * Carga las estadísticas de una colección
 */
function cargarEstadisticas() {
  const select = document.getElementById('coleccionSelect');
  if (!select) return;

  const coleccionId = select.value;

  // TODO: Cargar estadísticas desde /api/estadisticas/coleccion/{id}
  console.log('Cargar estadísticas para colección:', coleccionId || 'todas');
}

// ============================================
// INICIALIZACIÓN
// ============================================

// Cerrar modales al hacer click fuera de ellos
document.addEventListener('click', function(event) {
  if (event.target.classList.contains('modal-overlay')) {
    event.target.classList.remove('active');
  }
});

// Inicializar listeners del formulario de filtros si existe
document.addEventListener('DOMContentLoaded', function() {
  const filterForm = document.getElementById('filterForm');
  if (filterForm) {
    filterForm.addEventListener('submit', aplicarFiltros);
  }

  // Drag and drop para archivos
  const fileUploadArea = document.getElementById('fileUploadArea');
  if (fileUploadArea) {
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
      fileUploadArea.addEventListener(eventName, preventDefaults, false);
    });

    ['dragenter', 'dragover'].forEach(eventName => {
      fileUploadArea.addEventListener(eventName, () => {
        fileUploadArea.classList.add('dragover');
      }, false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
      fileUploadArea.addEventListener(eventName, () => {
        fileUploadArea.classList.remove('dragover');
      }, false);
    });

    fileUploadArea.addEventListener('drop', handleDrop, false);
  }
});

function preventDefaults(e) {
  e.preventDefault();
  e.stopPropagation();
}

function handleDrop(e) {
  const dt = e.dataTransfer;
  const files = dt.files;

  if (files.length > 0) {
    const input = document.getElementById('multimedia');
    if (input) {
      input.files = files;
      handleFileSelect({ target: input });
    }
  }
}

console.log('MetaMapa JS inicializado correctamente');
