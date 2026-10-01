# RegistroIncidencias

**Descripción:**
RegistroIncidencias es una aplicación móvil intuitiva y rápida para la captura de reportes. A través de una interfaz limpia construida con Jetpack Compose, permite a los usuarios ingresar los detalles de un evento o problema y visualizar la confirmación del reporte de forma inmediata en la misma pantalla.

## Propósito
Aplicación Android diseñada para el registro y gestión de incidencias.

## Herramientas utilizadas
* Android Studio
* Kotlin
* Jetpack Compose
* Git y GitHub

## Estado actual
* **Funciona:**
  * Pantalla inicial interactiva para la captura de incidencias (título y descripción).
  * Manejo de estado básico con `remember` y `mutableStateOf`.
  * **Teclado contextual e IME:** Configuración de `KeyboardOptions` con `capitalization = KeyboardCapitalization.Sentences`, además de acciones IME (`ImeAction.Next` e `ImeAction.Done`) para pasar automáticamente entre campos y ocultar el teclado.
  * **Interacción táctil de prioridad:** Selección dinámica de prioridad (*Baja*, *Media*, *Alta*) mediante componentes `Card` clicables (`Modifier.clickable`) con retroalimentación visual inmediata (resaltado de borde y color de contenedor).
  * Retroalimentación visual dinámica en la interfaz al presionar el botón "Crear reporte", mostrando el título en negrita, la prioridad seleccionada y la descripción dentro de una tarjeta.
  * Generación de marca de tiempo (fecha y hora exacta) generada automáticamente y mostrada en la parte inferior de la tarjeta del reporte.
  * Personalización de contraste y colores en campos de entrada para mejorar la legibilidad.
  * Optimizaciones de Experiencia de Usuario (UX): Limpieza automática de los campos de texto, ocultamiento del teclado virtual y remoción del cursor (foco) tras la preparación del reporte.
* **Falta implementar:** Validación avanzada de formularios, ciclo de vida y persistencia de datos (base de datos o API).

## Registro de avances
* **Semana 6:** Implementación de interfaz con estado reactivo, captura de texto en tiempo real, limpieza automática de campos y retroalimentación inmediata al usuario.
* **Semana 7:** Mejoras de Experiencia de Usuario (UX) y presentación visual. Se integró la gestión de enfoque (ocultamiento automático de teclado y cursor al guardar), mejora tipográfica en la tarjeta de datos (título en negrita y descripción normal) y captura de la fecha y hora exacta del sistema.
* **Semana 10:** Implementación de teclado contextual e interacción táctil. Integración de `KeyboardOptions` e `ImeAction` para navegación fluida de campos, y diseño de un selector táctil de prioridad (*Baja*, *Media*, *Alta*) utilizando `Modifier.clickable` con recomposición dinámica de interfaz.

##  Detalles Técnicos de la Semana 10

### 1. Entrada de Texto Contextual (`KeyboardOptions` y `KeyboardActions`)
* **Capitalización Automática:** Configuración de `KeyboardCapitalization.Sentences` para inicializar mayúsculas de manera automática en el inicio de frases.
* **Flujo de Foco Secuencial:** El campo *Título* hace uso de `ImeAction.Next` junto con `focusManager.moveFocus(FocusDirection.Down)` para transferir el foco al campo *Descripción* desde la acción del teclado virtual.
* **Cierre Programático:** El campo *Descripción* utiliza `ImeAction.Done` con `keyboardController?.hide()` y `focusManager.clearFocus()` para descartar el Soft Keyboard y limpiar la resalta del cursor en la UI.

### 2. Selector Táctil de Prioridad (`Modifier.clickable`)
* **Acciones Táctiles de Alto Nivel:** Se incorporó un componente selector compuesto por `Card`s individuales para definir la prioridad de la incidencia (*Baja*, *Media*, *Alta*).
* **Recomposición por Mutación de Estado:** El evento de toque activa la mutación del estado `prioridad`. Esto dispara una recomposición puntual en Jetpack Compose, actualizando dinámicamente las propiedades visuales `border` y `containerColor` del componente seleccionado.

## Cómo abrir el proyecto
1. Clonar este repositorio localmente.
2. Abrir Android Studio y seleccionar "Open".
3. Navegar hasta la carpeta del proyecto y esperar a que Gradle synchronice.

## Autor
José Manuel Palacios Carrillo  
Universidad Tecnológica de El Salvador